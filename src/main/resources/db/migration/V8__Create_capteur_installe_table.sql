CREATE TABLE t_e_capteurinstalle_cin (
    cin_id SERIAL PRIMARY KEY,
    cin_nombre INTEGER NOT NULL,
    cap_id INTEGER,
    sal_id INTEGER,
    FOREIGN KEY (cap_id) REFERENCES t_e_capteur_cap(cap_id),
    FOREIGN KEY (sal_id) REFERENCES t_e_salle_sal(sal_id)
);
