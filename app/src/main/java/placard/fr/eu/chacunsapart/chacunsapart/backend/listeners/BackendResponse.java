package placard.fr.eu.chacunsapart.chacunsapart.backend.listeners;

import placard.fr.eu.chacunsapart.chacunsapart.backend.beans.BackendObject;

public interface BackendResponse {
	public void result(int status, BackendObject obj);
}
