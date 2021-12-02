package ClassesUtilitarias;

import java.util.UUID;

//Classe para a geração de ID único 
public class IdGenerator {
	public static String gerarId() {
		return UUID.randomUUID().toString();
	}
}
