package util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Classe utilitária para validação de campos de formulário
 */
public class FieldValidator {

    /**
     * Valida se um CPF é válido
     * @param cpf CPF para validar (pode conter pontos e traços)
     * @return true se válido, false caso contrário
     */
    public static boolean validarCPF(String cpf) {
        if (cpf == null) return false;
        
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");
        
        // Verifica se tem 11 dígitos
        if (cpf.length() != 11) return false;
        
        // Verifica se todos os dígitos são iguais
        if (cpf.matches("(\\d)\\1{10}")) return false;
        
        try {
            // Calcula primeiro dígito verificador
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int digito1 = 11 - (soma % 11);
            if (digito1 > 9) digito1 = 0;
            
            // Calcula segundo dígito verificador
            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            int digito2 = 11 - (soma % 11);
            if (digito2 > 9) digito2 = 0;
            
            // Verifica se os dígitos calculados conferem
            return Character.getNumericValue(cpf.charAt(9)) == digito1 &&
                   Character.getNumericValue(cpf.charAt(10)) == digito2;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Valida se um CNPJ é válido
     * @param cnpj CNPJ para validar (pode conter pontos, traços e barras)
     * @return true se válido, false caso contrário
     */
    public static boolean validarCNPJ(String cnpj) {
        if (cnpj == null) return false;
        
        // Remove caracteres não numéricos
        cnpj = cnpj.replaceAll("[^0-9]", "");
        
        // Verifica se tem 14 dígitos
        if (cnpj.length() != 14) return false;
        
        // Verifica se todos os dígitos são iguais
        if (cnpj.matches("(\\d)\\1{13}")) return false;
        
        try {
            // Calcula primeiro dígito verificador
            int[] peso1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int soma = 0;
            for (int i = 0; i < 12; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * peso1[i];
            }
            int digito1 = 11 - (soma % 11);
            if (digito1 > 9) digito1 = 0;
            
            // Calcula segundo dígito verificador
            int[] peso2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            soma = 0;
            for (int i = 0; i < 13; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * peso2[i];
            }
            int digito2 = 11 - (soma % 11);
            if (digito2 > 9) digito2 = 0;
            
            // Verifica se os dígitos calculados conferem
            return Character.getNumericValue(cnpj.charAt(12)) == digito1 &&
                   Character.getNumericValue(cnpj.charAt(13)) == digito2;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Valida se um email tem formato válido
     * @param email Email para validar
     * @return true se válido, false caso contrário
     */
    public static boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) return false;
        
        // Regex básico para validação de email
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(regex);
    }
    
    /**
     * Valida se um telefone tem formato válido (10 ou 11 dígitos)
     * @param telefone Telefone para validar
     * @return true se válido, false caso contrário
     */
    public static boolean validarTelefone(String telefone) {
        if (telefone == null) return false;
        
        // Remove caracteres não numéricos
        String apenasNumeros = telefone.replaceAll("[^0-9]", "");
        
        // Telefone deve ter 10 (fixo) ou 11 (celular) dígitos
        return apenasNumeros.length() == 10 || apenasNumeros.length() == 11;
    }
    
    /**
     * Formata um CPF adicionando pontos e traço
     * @param cpf CPF sem formatação
     * @return CPF formatado (XXX.XXX.XXX-XX)
     */
    public static String formatarCPF(String cpf) {
        if (cpf == null) return "";
        
        String apenasNumeros = cpf.replaceAll("[^0-9]", "");
        
        if (apenasNumeros.length() > 11) {
            apenasNumeros = apenasNumeros.substring(0, 11);
        }
        
        StringBuilder formatado = new StringBuilder();
        for (int i = 0; i < apenasNumeros.length(); i++) {
            if (i == 3 || i == 6) {
                formatado.append(".");
            } else if (i == 9) {
                formatado.append("-");
            }
            formatado.append(apenasNumeros.charAt(i));
        }
        
        return formatado.toString();
    }
    
    /**
     * Formata um CNPJ adicionando pontos, traço e barra
     * @param cnpj CNPJ sem formatação
     * @return CNPJ formatado (XX.XXX.XXX/XXXX-XX)
     */
    public static String formatarCNPJ(String cnpj) {
        if (cnpj == null) return "";
        
        String apenasNumeros = cnpj.replaceAll("[^0-9]", "");
        
        if (apenasNumeros.length() > 14) {
            apenasNumeros = apenasNumeros.substring(0, 14);
        }
        
        StringBuilder formatado = new StringBuilder();
        for (int i = 0; i < apenasNumeros.length(); i++) {
            if (i == 2 || i == 5) {
                formatado.append(".");
            } else if (i == 8) {
                formatado.append("/");
            } else if (i == 12) {
                formatado.append("-");
            }
            formatado.append(apenasNumeros.charAt(i));
        }
        
        return formatado.toString();
    }
    
    /**
     * Formata um telefone adicionando parênteses e traço
     * @param telefone Telefone sem formatação
     * @return Telefone formatado (XX) XXXXX-XXXX ou (XX) XXXX-XXXX
     */
    public static String formatarTelefone(String telefone) {
        if (telefone == null) return "";
        
        String apenasNumeros = telefone.replaceAll("[^0-9]", "");
        
        if (apenasNumeros.length() > 11) {
            apenasNumeros = apenasNumeros.substring(0, 11);
        }
        
        if (apenasNumeros.length() < 2) {
            return apenasNumeros;
        }
        
        StringBuilder formatado = new StringBuilder();
        formatado.append("(").append(apenasNumeros.substring(0, 2)).append(") ");
        
        if (apenasNumeros.length() > 2) {
            if (apenasNumeros.length() <= 6) {
                formatado.append(apenasNumeros.substring(2));
            } else if (apenasNumeros.length() <= 10) {
                // Telefone fixo (8 dígitos)
                formatado.append(apenasNumeros.substring(2, 6));
                if (apenasNumeros.length() > 6) {
                    formatado.append("-").append(apenasNumeros.substring(6));
                }
            } else {
                // Celular (9 dígitos)
                formatado.append(apenasNumeros.substring(2, 7));
                if (apenasNumeros.length() > 7) {
                    formatado.append("-").append(apenasNumeros.substring(7));
                }
            }
        }
        
        return formatado.toString();
    }
    
    /**
     * Remove toda formatação de um campo (deixa apenas números)
     * @param valor Valor formatado
     * @return Apenas números
     */
    public static String removerFormatacao(String valor) {
        if (valor == null) return "";
        return valor.replaceAll("[^0-9]", "");
    }
    
    /**
     * DocumentFilter para CPF que formata automaticamente enquanto digita
     */
    public static class CPFDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            String text = fb.getDocument().getText(0, fb.getDocument().getLength());
            String newText = text.substring(0, offset) + string + text.substring(offset);
            String apenasNumeros = removerFormatacao(newText);
            
            if (apenasNumeros.length() <= 11) {
                super.remove(fb, 0, fb.getDocument().getLength());
                super.insertString(fb, 0, formatarCPF(apenasNumeros), attr);
            }
        }
        
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
            String apenasNumeros = removerFormatacao(newText);
            
            if (apenasNumeros.length() <= 11) {
                super.remove(fb, 0, fb.getDocument().getLength());
                super.insertString(fb, 0, formatarCPF(apenasNumeros), attrs);
            }
        }
        
        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            String text = fb.getDocument().getText(0, fb.getDocument().getLength());
            String newText = text.substring(0, offset) + text.substring(offset + length);
            String apenasNumeros = removerFormatacao(newText);
            
            super.remove(fb, 0, fb.getDocument().getLength());
            super.insertString(fb, 0, formatarCPF(apenasNumeros), null);
        }
    }
    
    /**
     * DocumentFilter para telefone que formata automaticamente enquanto digita
     */
    public static class TelefoneDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            String text = fb.getDocument().getText(0, fb.getDocument().getLength());
            String newText = text.substring(0, offset) + string + text.substring(offset);
            String apenasNumeros = removerFormatacao(newText);
            
            if (apenasNumeros.length() <= 11) {
                super.remove(fb, 0, fb.getDocument().getLength());
                super.insertString(fb, 0, formatarTelefone(apenasNumeros), attr);
            }
        }
        
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
            String apenasNumeros = removerFormatacao(newText);
            
            if (apenasNumeros.length() <= 11) {
                super.remove(fb, 0, fb.getDocument().getLength());
                super.insertString(fb, 0, formatarTelefone(apenasNumeros), attrs);
            }
        }
        
        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            String text = fb.getDocument().getText(0, fb.getDocument().getLength());
            String newText = text.substring(0, offset) + text.substring(offset + length);
            String apenasNumeros = removerFormatacao(newText);
            
            super.remove(fb, 0, fb.getDocument().getLength());
            super.insertString(fb, 0, formatarTelefone(apenasNumeros), null);
        }
    }
    
    /**
     * DocumentFilter para CNPJ que formata automaticamente enquanto digita
     */
    public static class CNPJDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            String text = fb.getDocument().getText(0, fb.getDocument().getLength());
            String newText = text.substring(0, offset) + string + text.substring(offset);
            String apenasNumeros = removerFormatacao(newText);
            
            if (apenasNumeros.length() <= 14) {
                super.remove(fb, 0, fb.getDocument().getLength());
                super.insertString(fb, 0, formatarCNPJ(apenasNumeros), attr);
            }
        }
        
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
            String apenasNumeros = removerFormatacao(newText);
            
            if (apenasNumeros.length() <= 14) {
                super.remove(fb, 0, fb.getDocument().getLength());
                super.insertString(fb, 0, formatarCNPJ(apenasNumeros), attrs);
            }
        }
        
        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            String text = fb.getDocument().getText(0, fb.getDocument().getLength());
            String newText = text.substring(0, offset) + text.substring(offset + length);
            String apenasNumeros = removerFormatacao(newText);
            
            super.remove(fb, 0, fb.getDocument().getLength());
            super.insertString(fb, 0, formatarCNPJ(apenasNumeros), null);
        }
    }
}
