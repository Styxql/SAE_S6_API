CREATE TABLE t_e_capteurinstalle_cin (
    cin_id SERIAL PRIMARY KEY,
    cin_numero INT NOT NULL,
    cin_capteur_id INTEGER,
    cin_salle_id INTEGER,
    FOREIGN KEY (capteur_id) REFERENCES t_e_capteur_cap(id),
    FOREIGN KEY (salle_id) REFERENCES t_e_salle_sal(id)
);
