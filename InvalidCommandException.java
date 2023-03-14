//exceptie aruncata in momentul citirii unei comenzi invalide
public class InvalidCommandException extends  Exception{
    public InvalidCommandException() {
        super("Comanda invalida!");
    }
}