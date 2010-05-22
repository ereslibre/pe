package practica3;

public class Par<T, S> {

	private T m_t = null;
	private S m_s = null;

	public Par() {
	}

	public Par(T t, S s) {
		m_t = t;
		m_s = s;
	}

	public T primero() {
		return m_t;
	}

	public void setPrimero(T t) {
		m_t = t;
	}

	public S segundo() {
		return m_s;
	}

	public void setSegundo(S s) {
		m_s = s;
	}

}
