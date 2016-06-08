package common;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class EntryPointRegistrar implements Registrar {
	
	private Map<Class<?>, Map<String, EntryPoint>> entryPoints;

	public EntryPointRegistrar() {
		init();
	}

	@PostConstruct
	@Override
	public void init() {
		entryPoints = new HashMap<Class<?>, Map<String, EntryPoint>>();
	}
	
	@Override
	public void add(Class<?> entryPointClass, EntryPoint newObject) {
		Map<String, EntryPoint> theEntryPoint = entryPoints.get(entryPointClass);
		if (theEntryPoint == null) {
			theEntryPoint = new HashMap<String, EntryPoint>();
			entryPoints.put(entryPointClass, theEntryPoint);
		}

		theEntryPoint.put(newObject.getIdentity(), newObject);
	}

	@Override
	public EntryPoint get(Class<?> entryPointClass, String objectName) {
		Map<String, EntryPoint> theEntryPoint = entryPoints.get(entryPointClass);
		return theEntryPoint.get(objectName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<? extends EntryPoint> getAll(Class<?> entryPointClass) {
		Map<String, EntryPoint> foundEntryPoints = entryPoints.get(entryPointClass);
		return (Collection<? extends EntryPoint>) Collections.unmodifiableCollection(
				foundEntryPoints != null ? entryPoints.get(entryPointClass).values() : Collections.EMPTY_SET);
	}

	@Override
	public EntryPoint delete(Class<?> entryPointClass, String objectName) {
		Map<String, EntryPoint> theEntryPoint = entryPoints.get(entryPointClass);
		return theEntryPoint.remove(objectName);
	}
}