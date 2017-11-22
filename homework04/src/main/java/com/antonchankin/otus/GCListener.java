package com.antonchankin.otus;

import com.sun.management.GarbageCollectionNotificationInfo;
import lombok.extern.slf4j.Slf4j;


import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class GCListener implements NotificationListener {
    //make listeners distinct
    private final int id;
    //keep a count of the total time spent in GCs
    private long totalGcDuration = 0;
    private String name = "NONE";
    private FileOutputStream fop = null;
    private final Object fileLock = new Object();

    public GCListener(int id) {
        this.id = id;
        log.info("I'm Listener #" + id);
    }

    public GCListener(int id, String name) {
        this.id = id;
        log.info("I'm Listener #" + id);
        this.name = name;
    }

    public static void installGCMonitoring(){
        //get all the GarbageCollectorMXBeans - there's one for each heap generation
        //so probably two - the old generation and young generation
        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
        //Install a GCListener for each bean
        int counter = 1;
        for (GarbageCollectorMXBean gcBean : gcBeans) {
            log.info("Next bean " + gcBean);
            NotificationEmitter emitter = (NotificationEmitter) gcBean;
            //use an anonymously generated listener for this example
            // - proper code should really use a named class
            NotificationListener listener = new GCListener(counter++, gcBean.getName());
            //Add the listener
            emitter.addNotificationListener(listener, null, null);
        }
    }

    @Override
    public void handleNotification(Notification notification, Object handback) {
        //we only handle GARBAGE_COLLECTION_NOTIFICATION notifications here
        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
            //get the information associated with this notification
            GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
            String gcName = info.getGcName();
            //get all the info and pretty print it
            long duration = info.getGcInfo().getDuration();
            String gcType = info.getGcAction();
            if ("end of minor GC".equals(gcType)) {
                gcType = "Young Gen GC";
            } else if ("end of major GC".equals(gcType)) {
                gcType = "Old Gen GC";
            }

            log.info("Listener#" + id + ": " + gcType + ": - " + info.getGcInfo().getId()+ " " + info.getGcName() + " (from " + info.getGcCause()+") "+duration + " milliseconds; start-end times " + info.getGcInfo().getStartTime()+ "-" + info.getGcInfo().getEndTime());
            log.info("Listener#" + id + ": " + " GcInfo CompositeType: " + info.getGcInfo().getCompositeType());
            log.info("Listener#" + id + ": " + " GcInfo MemoryUsageAfterGc: " + info.getGcInfo().getMemoryUsageAfterGc());
            log.info("Listener#" + id + ": " + " GcInfo MemoryUsageBeforeGc: " + info.getGcInfo().getMemoryUsageBeforeGc());

            //Get the information about each memory space, and pretty print it
            Map<String, MemoryUsage> memBefore = info.getGcInfo().getMemoryUsageBeforeGc();
            Map<String, MemoryUsage> mem = info.getGcInfo().getMemoryUsageAfterGc();
            long totalUsedBefore = 0;
            long totalUsedAfter = 0;
            long totalCommittedBefore = 0;
            long totalCommittedAfter = 0;
            long totalMaxBefore = 0;
            long totalMaxAfter = 0;
            long totalInitBefore = 0;
            long totalInitAfter = 0;
            for (Map.Entry<String, MemoryUsage> entry : mem.entrySet()) {
                String name = entry.getKey();
                MemoryUsage memdetail = entry.getValue();
                long memInit = memdetail.getInit();
                long memCommitted = memdetail.getCommitted();
                long memMax = memdetail.getMax();
                long memUsed = memdetail.getUsed();
                MemoryUsage before = memBefore.get(name);
                long beforepercent = ((before.getUsed()*1000L)/before.getCommitted());
                long percent = ((memUsed*1000L)/before.getCommitted()); //>100% when it gets expanded
                totalUsedBefore += before.getUsed();
                totalUsedAfter += memdetail.getUsed();
                totalCommittedBefore += before.getCommitted();
                totalCommittedAfter += memdetail.getCommitted();
                totalMaxBefore += before.getMax();
                totalMaxAfter += memdetail.getMax();
                totalInitBefore += before.getInit();
                totalInitAfter += memdetail.getInit();
                log.info("Listener#" + id + ": " + name + (memCommitted==memMax?"(fully expanded)":"(still expandable)") +"used: "+(beforepercent/10)+"."+(beforepercent%10)+"%->"+(percent/10)+"."+(percent%10)+"%("+((memUsed/1048576)+1)+"MB) / ");
            }
            totalGcDuration += info.getGcInfo().getDuration();
            
            record(gcType.substring(0,1),totalGcDuration, gcName, totalUsedBefore, totalUsedAfter, totalCommittedBefore, totalCommittedAfter, totalMaxBefore, totalMaxAfter, totalInitBefore, totalInitAfter);
            long percent = totalGcDuration*1000L/info.getGcInfo().getEndTime();
            log.info("Listener#" + id + ": " + "GC cumulative overhead "+(percent/10)+"."+(percent%10)+"%");
        }
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            if (fop != null) {
                fop.flush();
                fop.close();
            }
        } catch (IOException ex) {
            log.error("Cannot close file", ex);
        }
        super.finalize();
    }

    private void record(String gcType, long totalGcDuration, String gcName, long totalUsedBefore, long totalUsedAfter, long totalCommittedBefore, long totalCommittedAfter, long totalMaxBefore, long totalMaxAfter, long totalInitBefore, long totalInitAfter) {
        Date date = new Date();
        StringBuilder builder = new StringBuilder();
        builder.append(date.getTime());
        builder.append(',');
        builder.append(gcType);
        builder.append(',');
        builder.append(totalGcDuration);
        builder.append(',');
        builder.append(totalUsedBefore);
        builder.append(',');
        builder.append(totalUsedAfter);
        builder.append(',');
        builder.append(totalCommittedBefore);
        builder.append(',');
        builder.append(totalCommittedAfter);
        builder.append(',');
        builder.append(totalMaxBefore);
        builder.append(',');
        builder.append(totalMaxAfter);
        builder.append(',');
        builder.append(totalInitBefore);
        builder.append(',');
        builder.append(totalInitAfter);
        String line = builder.toString();
        appendToFile(line, gcName + ".csv");
    }

    private void appendToFile(String line, String fileName) {
        synchronized (fileLock) {
            try {
                if (Objects.isNull(fop)) {
                    File file = new File(fileName);
                    fop = new FileOutputStream(file);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    fop.write("TIMESTAMP,TYPE,DURATION,USED_BEFORE,USED_AFTER,COMMITTED_BEFORE,COMMITTED_AFTER,MAX_BEFORE,MAX_AFTER,INIT_BEFORE,INIT_AFTER\n".getBytes());
                }
                // get the content in bytes
                if (fop != null) {
                    line += '\n';
                    byte[] contentInBytes = line.getBytes();
                    fop.write(contentInBytes);
                    fop.flush();
                    log.debug("Done writing to " + fileName + " line :" + line);
                }
            } catch (IOException e) {
                log.error("Cannot write to file", e);
            }
        }
    }
}
