package org.eu.fr.placard.chacunsapartsdk.listeners;

import org.eu.fr.placard.chacunsapartsdk.beans.BackendObject;

public interface BackendResponse {
	public void result(int status, BackendObject obj);
}
