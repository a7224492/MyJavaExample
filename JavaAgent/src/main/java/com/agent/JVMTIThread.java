package com.agent;

import com.sun.tools.attach.*;
import java.io.IOException;
import java.util.List;

/**
 * @author jiangzhen
 */
public class JVMTIThread
{
	public static void main(String[] args)
		throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException
	{
		List<VirtualMachineDescriptor> list = VirtualMachine.list();
		for (VirtualMachineDescriptor vmd : list) {
			if (vmd.displayName().endsWith("com.mycode.research.jvm.JvmTest")) {
				VirtualMachine virtualMachine = VirtualMachine.attach(vmd.id());
				virtualMachine.loadAgent("F:\\QuanGuoProject\\out\\artifacts\\JavaAgent_jar\\JavaAgent.jar", "cxs");
				System.out.println("ok");
				virtualMachine.detach();
			}
		}
	}
}
