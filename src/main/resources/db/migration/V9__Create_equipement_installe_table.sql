CREATE TABLE t_e_equipementinstalle_ein (
    ein_id SERIAL PRIMARY KEY,
    ein_nombre INT NOT NULL,
    ein_equipement_id INTEGER,
    ein_salle_id INTEGER,
    FOREIGN KEY (equipement_id) REFERENCES t_e_equipement_eqp(id),
    FOREIGN KEY (salle_id) REFERENCES t_e_salle_sal(id)
);
