package placard.fr.eu.chacunsapart.chacunsapart.backend.listeners;

import placard.fr.eu.chacunsapart.chacunsapart.backend.beans.BackendObject;
import placard.fr.eu.chacunsapart.chacunsapart.backend.exceptions.BackendException;

public interface BackendListener {
	public void onBackendResponse(BackendObject bo);
	
	public void onBackendError(BackendException be);
}
