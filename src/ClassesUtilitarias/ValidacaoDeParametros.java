package ClassesUtilitarias;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
// Framework disponível em https://github.com/caelum/caelum-stella
public class ValidacaoDeParametros {
	public static boolean valida(String cpf) throws InvalidStateException{ 
		CPFValidator cpfValidator = new CPFValidator(); 
		cpfValidator.assertValid(cpf); 
		return true;
	}
}
