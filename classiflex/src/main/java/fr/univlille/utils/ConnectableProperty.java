package fr.univlille.utils;

public class ConnectableProperty extends ObservableProperty implements Observer {

	protected boolean propagating;

	public ConnectableProperty() {
		propagating = false;
	}

	@Override
	public void update(Observable observable) {
		// never called so does not do anything
	}

	@Override
	public void update(Observable other, Object data) {
		setValue(data);
	}

	public void biconnectTo(ConnectableProperty other) {
		connectTo(other);
		update(other, other.getValue());
		other.connectTo(this);
	}

	public void connectTo(ConnectableProperty other) {
		other.attach(this);
	}

	public void unconnectFrom(ConnectableProperty other) {
		other.detach(this);
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object val) {
		if (! propagating) {
			propagating = true;
			super.setValue(val);
			propagating = false;
		}
	}
}
