package placard.fr.eu.chacunsapart.backend.listeners;

import placard.fr.eu.chacunsapart.backend.beans.BackendObject;

public interface BackendResponse {
	public void result(int status, BackendObject obj);
}
