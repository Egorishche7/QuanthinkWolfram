package by.quantumquartet.quanthink.modelDAO;

public class DAOException extends Exception {

	// ЗАГЛУШКА
	private static final long serialVersionUID = 3432116957146249062L;

	public DAOException(String s) {
        super(s);
    }

	public DAOException(Exception e) {
		super(e);
	}

}
