package util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class FieldValidator {

    public static boolean validarCPF(String cpf) {
        if (cpf == null) return false;
        
        cpf = cpf.replaceAll("[^0-9]", "");
        
        if (cpf.length() != 11) return false;
        
        if (cpf.matches("(\\d)\\1{10}")) return false;
        
        try {
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int digito1 = 11 - (soma % 11);
            if (digito1 > 9) digito1 = 0;
            
            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            int digito2 = 11 - (soma % 11);
            if (digito2 > 9) digito2 = 0;
            
            return Character.getNumericValue(cpf.charAt(9)) == digito1 &&
                   Character.getNumericValue(cpf.charAt(10)) == digito2;
        } catch (Exception e) {
            return false;
        }
    }
    
    public static boolean validarCNPJ(String cnpj) {
        if (cnpj == null) return false;
        
        cnpj = cnpj.replaceAll("[^0-9]", "");
        
        if (cnpj.length() != 14) return false;
        
        if (cnpj.matches("(\\d)\\1{13}")) return false;
        
        try {
            int[] peso1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int soma = 0;
            for (int i = 0; i < 12; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * peso1[i];
            }
            int digito1 = 11 - (soma % 11);
            if (digito1 > 9) digito1 = 0;
            
            int[] peso2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            soma = 0;
            for (int i = 0; i < 13; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * peso2[i];
            }
            int digito2 = 11 - (soma % 11);
            if (digito2 > 9) digito2 = 0;
            
            return Character.getNumericValue(cnpj.charAt(12)) == digito1 &&
                   Character.getNumericValue(cnpj.charAt(13)) == digito2;
        } catch (Exception e) {
            return false;
        }
    }
    
    public static boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) return false;
        
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(regex);
    }
    
    public static boolean validarTelefone(String telefone) {
        if (telefone == null) return false;
        
        String apenasNumeros = telefone.replaceAll("[^0-9]", "");
        
        return apenasNumeros.length() == 10 || apenasNumeros.length() == 11;
    }
    
    /**
     * Valida CPF ou CNPJ automaticamente com base na quantidade de dígitos.
     * Retorna true se for CPF válido (11 dígitos) ou CNPJ válido (14 dígitos).
     */
    public static boolean validarCpfCnpj(String valor) {
        if (valor == null) return false;
        String apenasNumeros = removerFormatacao(valor);
        if (apenasNumeros.length() == 11) {
            return validarCPF(apenasNumeros);
        } else if (apenasNumeros.length() == 14) {
            return validarCNPJ(apenasNumeros);
        }
        return false;
    }
    
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
                formatado.append(apenasNumeros.substring(2, 6));
                if (apenasNumeros.length() > 6) {
                    formatado.append("-").append(apenasNumeros.substring(6));
                }
            } else {
                formatado.append(apenasNumeros.substring(2, 7));
                if (apenasNumeros.length() > 7) {
                    formatado.append("-").append(apenasNumeros.substring(7));
                }
            }
        }
        
        return formatado.toString();
    }
    
    public static String removerFormatacao(String valor) {
        if (valor == null) return "";
        return valor.replaceAll("[^0-9]", "");
    }
    
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
    
    // Documento filter que suporta CPF (11 dígitos) e CNPJ (14 dígitos).
    public static class CpfCnpjDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            String text = fb.getDocument().getText(0, fb.getDocument().getLength());
            String newText = text.substring(0, offset) + string + text.substring(offset);
            String apenasNumeros = removerFormatacao(newText);

            if (apenasNumeros.length() <= 14) {
                super.remove(fb, 0, fb.getDocument().getLength());
                if (apenasNumeros.length() <= 11) {
                    super.insertString(fb, 0, formatarCPF(apenasNumeros), attr);
                } else {
                    super.insertString(fb, 0, formatarCNPJ(apenasNumeros), attr);
                }
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
                if (apenasNumeros.length() <= 11) {
                    super.insertString(fb, 0, formatarCPF(apenasNumeros), attrs);
                } else {
                    super.insertString(fb, 0, formatarCNPJ(apenasNumeros), attrs);
                }
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            String text = fb.getDocument().getText(0, fb.getDocument().getLength());
            String newText = text.substring(0, offset) + text.substring(offset + length);
            String apenasNumeros = removerFormatacao(newText);

            super.remove(fb, 0, fb.getDocument().getLength());
            if (apenasNumeros.length() <= 11) {
                super.insertString(fb, 0, formatarCPF(apenasNumeros), null);
            } else {
                super.insertString(fb, 0, formatarCNPJ(apenasNumeros), null);
            }
        }
    }
}