package com.airfrance.repind.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Aspect
@Component
public class AspectLogger {

	private final Log log = LogFactory.getLog(this.getClass());

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Loggable {
	};
	
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface LoggableNoParams {
	};
	
	@Around("execution(* *(..)) && @annotation(loggablenoparams)")
	public Object logByAnnotationNoParams(ProceedingJoinPoint joinPoint, LoggableNoParams loggablenoparams) throws Throwable {
		return logTimeMethodNoParams(joinPoint);
	}
	
	public Object logTimeMethodNoParams(ProceedingJoinPoint joinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		Object retVal = joinPoint.proceed();

		if (joinPoint.getTarget() == null) {
			return retVal;
		}
		stopWatch.stop();
		StringBuffer logMessage = new StringBuffer();
		logMessage.append(joinPoint.getTarget().getClass().getName());
		logMessage.append(".");
		logMessage.append(joinPoint.getSignature().getName());
		logMessage.append("(");
		logMessage.append(")");
		logMessage.append(" execution time: ");
		logMessage.append(stopWatch.getTotalTimeMillis());
		logMessage.append(" ms");
		log.info(logMessage.toString());

		return retVal;
	}

	@Around("execution(* *(..)) && @annotation(loggable)")
	public Object logByAnnotation(ProceedingJoinPoint joinPoint, Loggable loggable) throws Throwable {
		return logTimeMethod(joinPoint);
	}

	public Object logTimeMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		Object retVal = joinPoint.proceed();

		if (joinPoint.getTarget() == null) {
			return retVal;
		}
		stopWatch.stop();
		StringBuffer logMessage = new StringBuffer();
		logMessage.append(joinPoint.getTarget().getClass().getName());
		logMessage.append(".");
		logMessage.append(joinPoint.getSignature().getName());
		logMessage.append("(");
		// append args
		Object[] args = joinPoint.getArgs();
		for (Object arg : args) {
			logMessage.append(arg).append(",");
		}
		if (args.length > 0) {
			logMessage.deleteCharAt(logMessage.length() - 1);
		}

		logMessage.append(")");
		logMessage.append(" execution time: ");
		logMessage.append(stopWatch.getTotalTimeMillis());
		logMessage.append(" ms");
		log.info(logMessage.toString());

		return retVal;
	}
}
