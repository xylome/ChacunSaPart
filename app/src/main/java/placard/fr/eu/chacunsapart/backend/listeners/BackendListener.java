package placard.fr.eu.chacunsapart.backend.listeners;

import placard.fr.eu.chacunsapart.backend.beans.BackendObject;
import placard.fr.eu.chacunsapart.backend.exceptions.BackendException;

public interface BackendListener {
	public void onBackendResponse(BackendObject bo);
	
	public void onBackendError(BackendException be);
}
