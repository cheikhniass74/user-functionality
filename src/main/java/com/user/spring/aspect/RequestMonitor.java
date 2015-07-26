package com.user.spring.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.user.spring.controller.RootController;

@Component
@Aspect
public class RequestMonitor {
	private static final Log LOG = LogFactory.getLog(RootController.class);

	@Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public Object wrap(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {
		LOG.info("Before Controller method "
				+ proceedingJoinPoint.getSignature().getName() + "Thread name "
				+ Thread.currentThread().getName());

		Object reuestMethod = proceedingJoinPoint.proceed();

		LOG.info("After controller method "
				+ proceedingJoinPoint.getSignature().getName()
				+ "execution complete");

		return reuestMethod;

	}

}
