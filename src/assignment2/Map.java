package assignment2;

public class Map<K extends Data<K>, V extends Clonable<V>> implements MapInterface<K, V> {

	private static final String CLONE_EXCEPTION = "Could not clone Map. This should never happen.";

	private List<MapEntry> elements;

	public Map() {
		this.elements = new List<>();
	}

	public Map<K, V> init() {
		elements.init();
		return this;
	}

	public void put(K key, V value) {
		elements.insert(new MapEntry(key, value));
	}

	public void remove(K key) {
		if (elements.find(new MapEntry(key))) {
			elements.remove();
		}
	}

	public V get(K key) {
		for (int i = 0; i < elements.size(); i++) {
			if (elements.find(new MapEntry(key))) {
				return elements.retrieve().value;
			}
		}

		// this should never be executed
		return null;
	}

	public boolean isEmpty() {
		return (elements.isEmpty());
	}

	public int size() {
		return elements.size();
	}

	public boolean contains(K key) {
		for (int i = 0; i < elements.size(); i++) {
			if (elements.find(new MapEntry(key))) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Map<K, V> clone() {
		Map<K, V> result;

		try {
			//noinspection unchecked
			result = (Map<K, V>) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new Error(CLONE_EXCEPTION);
		}

		result.elements = elements.clone();

		return result;
	}

	private class MapEntry implements Data<MapEntry> {

		public static final String CLONE_EXCEPTION = "Could not clone MapEntry. This should never happen.";

		private K key;
		private V value;

		@SuppressWarnings("unused")
		public MapEntry() {
			this.key = null;
			this.value = null;
		}

		public MapEntry(K key) {
			this.key = key.clone();
			this.value = null;
		}

		public MapEntry(K key, V value) {
			this.key = key.clone();
			this.value = value.clone();
		}

		public MapEntry clone() {
			MapEntry result;

			try {
				//noinspection unchecked
				result = (MapEntry) super.clone();
			} catch (CloneNotSupportedException e) {
				throw new Error(CLONE_EXCEPTION);
			}

			result.key = key.clone();
			result.value = value.clone();

			return result;
		}

		@Override
		@SuppressWarnings("NullableProblems")
		public int compareTo(MapEntry that) {
			return this.key.compareTo(that.key);
		}
	}

}
