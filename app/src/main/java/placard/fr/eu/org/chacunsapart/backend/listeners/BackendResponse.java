package placard.fr.eu.org.chacunsapart.backend.listeners;

import placard.fr.eu.org.chacunsapart.backend.beans.BackendObject;

public interface BackendResponse {
	public void result(int status, BackendObject obj);
}
