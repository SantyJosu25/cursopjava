package ec.tda.curso.dobleautenticacion_ejb.utilitarios;

public class AlgoritmoGeneradorCodigo {

    public static void main(String[] args) {
        AlgoritmoGeneradorCodigo agc = new AlgoritmoGeneradorCodigo();
        agc.creaPass();
    }

    private String pass;

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void creaPass() {
        try {

            pass = "";
            for (int i = 0; i < 5; i++) {
                int el = (int) (Math.random() * 64);
                conjunto[i] = elementos[el];
                pass = new String(conjunto);

            }

            System.out.println("PASS: " + pass.trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private char[] elementos = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
        'h', 'i', 'j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A',
        'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
        'V', 'W', 'X', 'Y', 'Z'};

    private char[] conjunto = new char[5];
}
