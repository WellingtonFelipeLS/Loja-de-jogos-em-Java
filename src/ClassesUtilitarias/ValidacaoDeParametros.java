package ClassesUtilitarias;

import br.com.caelum.stella.validation.CPFValidator;
// Framework disponível em https://github.com/caelum/caelum-stella
public class ValidacaoDeParametros {
	public static boolean valida(String cpf) { 
		CPFValidator cpfValidator = new CPFValidator(); 
		try{ cpfValidator.assertValid(cpf); 
		return true; 
		}catch(Exception e){ 
			e.printStackTrace(); 
			return false; 
			}
		}
}
