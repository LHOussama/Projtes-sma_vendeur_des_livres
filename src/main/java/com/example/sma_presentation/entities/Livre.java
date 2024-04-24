package com.example.sma_presentation.entities;
import java.util.UUID;
public class Livre {
    private String id;
    private String nomLivre;
    private String NomAuteur;
    private double prix;
    private String vendeur;

    public Livre(String nomLivre, String nomAuteur, double prix, String vendeur) {
        this.id = UUID.randomUUID().toString();
        this.nomLivre = nomLivre;
        this.NomAuteur = nomAuteur;
        this.prix = prix;
        this.vendeur = vendeur;
    }
    public Livre() {
    }
    public Livre(String id, String nomLivre, String nomAuteur, double prix, String vendeur) {
        this.id = id;
        this.nomLivre = nomLivre;
        this.NomAuteur = nomAuteur;
        this.prix = prix;
        this.vendeur = vendeur;
    }
    public String getId() {
        return id;
    }

    public String getNomLivre() {
        return nomLivre;
    }
    public void setNomLivre(String nomLivre) {
        this.nomLivre = nomLivre;
    }

    public String getNomAuteur() {
        return NomAuteur;
    }

    public void setNomAuteur(String nomAuteur) {
        NomAuteur = nomAuteur;
    }
    public double getPrix() {
        return prix;
    }
    public void setPrix(double prix) {
        this.prix = prix;
    }
    public String getVendeur() {
        return vendeur;
    }
    public void setVendeur(String vendeur) {
        this.vendeur = vendeur;
    }
    @Override
    public String toString() {
        return "Livre{" +
                "id='" + id + '\'' +
                ", nomLivre='" + nomLivre + '\'' +
                ", NomAuteur='" + NomAuteur + '\'' +
                ", prix=" + prix +
                ", vendeur='" + vendeur + '\'' +
                '}';
    }
    public static Livre fromString(String livreString) {
        livreString = livreString.replaceAll("\\s+", "");
        String[] attributs = livreString.split(",|\\{|}");
        String id = null;
        String nomLivre = null;
        String nomAuteur = null;
        double prix = 0.0;
        String vendeur = null;
        for (String attribut : attributs) {
            String[] keyValue = attribut.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1].replaceAll("'", "");
                if (key.equals("id")) {
                    id = value;
                } else if (key.equals("nomLivre")) {
                    nomLivre = value;
                } else if (key.equals("NomAuteur")) {
                    nomAuteur = value;
                } else if (key.equals("prix")) {
                    prix = Double.parseDouble(value);
                } else if (key.equals("vendeur")) {
                    vendeur = value;
                }
            }
        }
        return new Livre(id, nomLivre, nomAuteur, prix, vendeur);
    }
}
