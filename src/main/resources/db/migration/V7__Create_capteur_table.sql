CREATE TABLE t_e_capteur_cap (
    cap_id SERIAL PRIMARY KEY,
    cap_nom VARCHAR(255) NOT NULL,
    cap_description TEXT,
    cap_dimensions VARCHAR(255),
    cap_image_url VARCHAR(255),
    cap_marque_id INTEGER,
    FOREIGN KEY (marque_id) REFERENCES t_e_marque_maq(id)
);
