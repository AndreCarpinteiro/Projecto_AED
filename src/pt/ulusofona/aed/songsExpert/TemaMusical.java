package pt.ulusofona.aed.songsExpert;
import java.util.ArrayList;

public class TemaMusical {
    String id;
    String titulo;
    ArrayList <Artista> artistasEnvolvidos = new ArrayList<>();
    int ano;

    public TemaMusical(String id, String Titulo, ArrayList<Artista> artistasEnvolvidos, int Ano){
        this.id = id;
        this.titulo = Titulo;
        this.artistasEnvolvidos = artistasEnvolvidos;
        this.ano = Ano;
    }

    TemaMusical(){
    }

    public String toString() {

        String stringOut;

        if(artistasEnvolvidos.size() > 1 && artistasEnvolvidos.get(1) != null){

            if (ano == 0) {
                stringOut = id + " | " + titulo + " | " + "Ano desconhecido" + " | ";
                for (int i = 0; i < artistasEnvolvidos.size(); i++) {
                    if(i > 0){
                        stringOut += " / ";
                    }
                    stringOut += artistasEnvolvidos.get(i).nome + " (" + artistasEnvolvidos.get(i).numeroDeMusicas + " - " + artistasEnvolvidos.get(i).artistasSemelhantes.size() + ")";
                }
            } else {
                stringOut = id + " | " + titulo + " | " + ano + " | ";
                for (int i = 0; i < artistasEnvolvidos.size(); i++) {
                    if(i > 0){
                        stringOut += " / ";
                    }
                    stringOut += artistasEnvolvidos.get(i).nome + " (" + artistasEnvolvidos.get(i).numeroDeMusicas + " - " + artistasEnvolvidos.get(i).artistasSemelhantes.size() + ")";
                }
            }
        }else{
            if (ano == 0) {
                stringOut = id + " | " + titulo + " | " + "Ano desconhecido" + " | " + artistasEnvolvidos.get(0).nome + " (" + artistasEnvolvidos.get(0).numeroDeMusicas + " - " + artistasEnvolvidos.get(0).artistasSemelhantes.size() + ")";
            } else {
                stringOut = id + " | " + titulo + " | " + ano + " | " + artistasEnvolvidos.get(0).nome + " (" + artistasEnvolvidos.get(0).numeroDeMusicas + " - " + artistasEnvolvidos.get(0).artistasSemelhantes.size() + ")";
            }
        }
        return stringOut;
    }
}