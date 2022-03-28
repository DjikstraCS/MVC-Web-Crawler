package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import controller.Controller;

public class TaskExecutor
{
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    Controller controller;
    volatile boolean isStopIssued;

    public TaskExecutor(Controller controller) 
    {
    	this.controller = controller;

    }

    public void startExecutionAt(int targetHour, int targetMin, int targetSec) {
    	
        Runnable taskWrapper = new Runnable(){

            @Override
            public void run() 
            {
                System.out.println("TaskExecutor: Running runSeachItems()...");
                
                
            	controller.runSearchItems();
            	startExecutionAt(targetHour, targetMin, targetSec);
            	
                //System.out.println("Timer Set!");
            }

        };
        long delay = computeNextDelay(targetHour, targetMin, targetSec);
        executorService.schedule(taskWrapper, delay, TimeUnit.SECONDS);
        System.out.println("schedule set");
    }

    private long computeNextDelay(int targetHour, int targetMin, int targetSec) 
    {
        LocalDateTime localNow = LocalDateTime.now();
        System.out.println("LoaclDateTime: " + LocalDateTime.now());
        ZoneId currentZone = ZoneId.systemDefault();
        ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);
        System.out.println("ZonedNow: " + zonedNow.toString() + " Taget Hour: " + targetHour + " Taget Hour: " + targetMin);
        ZonedDateTime zonedNextTarget = zonedNow.withHour(targetHour).withMinute(targetMin).withSecond(targetSec);
        System.out.println("ZonedNextTarget: " + zonedNextTarget.toString());
        if(zonedNow.compareTo(zonedNextTarget) > 0)
            zonedNextTarget = zonedNextTarget.plusDays(1);
        
        System.out.println("ZonedNextTarget after comparence: " + zonedNextTarget.toString());

        Duration duration = Duration.between(zonedNow, zonedNextTarget);
        System.out.println("Hours to next run: " + (duration.getSeconds()/60)/60 + "Seconds to next run: " + (duration.getSeconds()));
        return duration.getSeconds();
    }

    public void stop()
    {
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
            //Logger.getLogger(MyTaskExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}