package net.bolbat.kit.scheduler;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Task parameter value.
 *
 * @author ivanbatura
 */
@JsonTypeInfo (use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface TaskParameterValue extends Serializable {
}
