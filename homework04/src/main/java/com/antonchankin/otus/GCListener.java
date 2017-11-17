package com.antonchankin.otus;

import com.sun.management.GarbageCollectionNotificationInfo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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

@Slf4j
public class GCListener implements NotificationListener {
    //make listeners distinct
    private final int id;
    //keep a count of the total time spent in GCs
    private long totalGcDuration = 0;
    private File recorder;
    private FileOutputStream fop = null;
    private String name = "NONE";

    public GCListener(int id) {
        this.id = id;
        log.info("I'm Listener #" + id);
        this.recorder = new File("gc_" + id + ".csv");
    }

    public GCListener(int id, String name) {
        this.id = id;
        log.info("I'm Listener #" + id);
        this.name = name;
        this.recorder = new File("gc_" + name + ".csv");
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

                log.info("Listener#" + id + ": " + name + (memCommitted==memMax?"(fully expanded)":"(still expandable)") +"used: "+(beforepercent/10)+"."+(beforepercent%10)+"%->"+(percent/10)+"."+(percent%10)+"%("+((memUsed/1048576)+1)+"MB) / ");
            }
            totalGcDuration += info.getGcInfo().getDuration();
            
            record(gcType,totalGcDuration);
            long percent = totalGcDuration*1000L/info.getGcInfo().getEndTime();
            log.info("Listener#" + id + ": " + "GC cumulative overhead "+(percent/10)+"."+(percent%10)+"%");
        }
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            if (fop != null) {
                fop.close();
            }
        } catch (Exception e) {
            log.error("Cannot close recorder", e);
        }
        super.finalize();
    }

    private void record(String gcType, long totalGcDuration) {
        try {
            boolean isThereAFile = recorder.exists();
            if (!isThereAFile) {
                isThereAFile = recorder.createNewFile();
            }
            if (isThereAFile) {
                fop = fop != null ? fop : new FileOutputStream(recorder);
            } else {
                log.warn("Cannot write statistics: no file");
            }
            if (fop != null) {
                Date date = new Date();
                String line = "" + date.getTime() + ',' + gcType + ',' + totalGcDuration;
                byte[] contentInBytes = line.getBytes();
                fop.write(contentInBytes);
                fop.flush();
            }
        } catch (IOException e) {
            log.error("Failed to write statistics", e);
        }
    }
}
