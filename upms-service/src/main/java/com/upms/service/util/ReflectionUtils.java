package com.upms.service.util;

import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class ReflectionUtils {

	private final static int STACK_DEPTH = 20;

	static private ThreadMXBean threadBean = ManagementFactory
			.getThreadMXBean();

	public static void printThreadInfo(PrintWriter stream, String title) {
		boolean contention = threadBean.isThreadContentionMonitoringEnabled();
		long[] threadIds = threadBean.getAllThreadIds();
		stream.println("Process Thread Dump: " + title);
		stream.println(threadIds.length + " active threads");
		for (long tid : threadIds) {
			ThreadInfo info = threadBean.getThreadInfo(tid, STACK_DEPTH);
			if (info == null) {
				stream.println("  Inactive");
				continue;
			}
			stream.println("Thread "
					+ getTaskName(info.getThreadId(), info.getThreadName())
					+ ":");
			Thread.State state = info.getThreadState();
			stream.println("  State: " + state);
			stream.println("  Blocked count: " + info.getBlockedCount());
			stream.println("  Waited count: " + info.getWaitedCount());
			if (contention) {
				stream.println("  Blocked time: " + info.getBlockedTime());
				stream.println("  Waited time: " + info.getWaitedTime());
			}
			if (state == Thread.State.WAITING) {
				stream.println("  Waiting on " + info.getLockName());
			} else if (state == Thread.State.BLOCKED) {
				stream.println("  Blocked on " + info.getLockName());
				stream.println("  Blocked by "
						+ getTaskName(info.getLockOwnerId(),
								info.getLockOwnerName()));
			}
			stream.println("  Stack:");
			for (StackTraceElement frame : info.getStackTrace()) {
				stream.println("    " + frame.toString());
			}
		}
		stream.flush();
	}

	private static String getTaskName(long id, String name) {
		if (name == null) {
			return Long.toString(id);
		}
		return id + " (" + name + ")";
	}
}
