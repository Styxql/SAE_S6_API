CREATE TABLE t_e_salle_sal (
    sal_id SERIAL PRIMARY KEY,
    sal_nom VARCHAR(255) NOT NULL,
    sal_image_url VARCHAR(255),
    sal_capacite INT,
    sal_batiment_id INTEGER,
    sal_type_salle_id INTEGER,
    FOREIGN KEY (batiment_id) REFERENCES t_e_batiment_bat(id),
    FOREIGN KEY (type_salle_id) REFERENCES t_e_typesalle_typ(id)
);
