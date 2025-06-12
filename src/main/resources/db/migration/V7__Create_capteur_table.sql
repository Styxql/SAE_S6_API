CREATE TABLE t_e_capteur_cap (
    cap_id SERIAL PRIMARY KEY,
    cap_nom VARCHAR(255) NOT NULL,
    cap_reference VARCHAR(200),
    cap_description VARCHAR(500),
    cap_hauteur NUMERIC(5,2),
    cap_longueur NUMERIC(5,2),
    cap_largeur NUMERIC(5,2),
    cap_urlimg VARCHAR(1024),
    mar_id INTEGER,
    FOREIGN KEY (mar_id) REFERENCES t_e_marque_mar(mar_id)
);
