package net.bolbat.kit.scheduler;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.quartz.Job;

/**
 * Scheduled job.
 *
 * @author ivanbatura
 */
@JsonTypeInfo (use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface Task extends Job {

}
