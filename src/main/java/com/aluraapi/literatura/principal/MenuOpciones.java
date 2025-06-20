package com.aluraapi.literatura.principal;

import com.aluraapi.literatura.procesos.*;
import com.aluraapi.literatura.service.AutorService;
import com.aluraapi.literatura.service.LibroService;
import com.aluraapi.literatura.procesos.LibrosRegistradosPorIdioma;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuOpciones extends JFrame {
    private final LibroService libroService;
    private final AutorService autorService;
    private final JPanel panelContenedorBotones;

    public MenuOpciones(LibroService libroService, AutorService autorService) {
        this.libroService = libroService;
        this.autorService = autorService;

        setTitle("LiterAlura 📚");
        setSize(1920, 1080);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ImageIcon fondoIcon = new ImageIcon(getClass().getResource("/imagenes/images.jpg"));
        Image img = fondoIcon.getImage();
        Image scaledImg = img.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
        JLabel fondoLabel = new JLabel(new ImageIcon(scaledImg));
        fondoLabel.setLayout(new GridBagLayout());
        setContentPane(fondoLabel);

        panelContenedorBotones = new JPanel();
        panelContenedorBotones.setLayout(new BoxLayout(panelContenedorBotones, BoxLayout.Y_AXIS));
        panelContenedorBotones.setOpaque(false);

        JPanel panelFondoBotones = new JPanel();
        panelFondoBotones.setBackground(new Color(0, 0, 0, 150));
        panelFondoBotones.setLayout(new GridBagLayout());
        panelFondoBotones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.gridx = 0;
        gbcPanel.gridy = 0;
        gbcPanel.anchor = GridBagConstraints.CENTER;
        panelFondoBotones.add(panelContenedorBotones, gbcPanel);

        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        gbcMain.weightx = 1.0;
        gbcMain.weighty = 1.0;
        gbcMain.anchor = GridBagConstraints.CENTER;
        fondoLabel.add(panelFondoBotones, gbcMain);

        agregarBotones();

        setVisible(true);
    }
    private void agregarBotones() {

        agregarBoton("Buscar libro por título", () -> {
            BusquedaLibroPorTitulo proceso = new BusquedaLibroPorTitulo(libroService, this);
            proceso.ejecutar();
        });
        agregarBoton("Buscar autor por nombre", () -> {
            BuscarAutorPorNombre proceso = new BuscarAutorPorNombre(libroService, this);
            proceso.ejecutar();
        });
        agregarBoton("Listar libros registrados", () -> {
            ListarLibrosRegistrados proceso = new ListarLibrosRegistrados(libroService, this);
            proceso.ejecutar();
        });
        agregarBoton("Listar autores registrados", () -> {
            ListarAutoresRegistrados proceso = new ListarAutoresRegistrados(autorService, this);
            proceso.ejecutar();
        });
        agregarBoton("Autores vivos en un año", () -> {
            AutoresVivosEnAnio proceso = new AutoresVivosEnAnio(autorService, this);
            proceso.ejecutar();
        });
        agregarBoton("Autores vivos entre años", () -> {
            AutoresVivosEntreAnios proceso = new AutoresVivosEntreAnios(autorService, this);
            proceso.ejecutar();
        });
        agregarBoton("Listar libros por idioma (Registrados)", () -> {
            LibrosRegistradosPorIdioma proceso = new LibrosRegistradosPorIdioma(libroService, this);
            proceso.ejecutar();
        });
        agregarBoton("Listar 13 libros por idioma (Gutendex)", () -> {
            Listar13LibrosPorIdiomaGutendex proceso = new Listar13LibrosPorIdiomaGutendex(libroService,
                    this);
            proceso.ejecutar();
        });
        agregarBoton("Top 13 desde Gutendex", () -> {
            Top13Gutendex proceso = new Top13Gutendex(libroService, this);
            proceso.ejecutar();
        });
        agregarBoton("Top 13 desde registro local", () -> {
            Top13RegistroLocal proceso = new Top13RegistroLocal(libroService, this);
            proceso.ejecutar();
        });
        agregarBoton("Salir", () -> System.exit(0));

    }
    private void agregarBoton(String texto, Runnable accion) {
        JButton boton = new JButton(texto);
        boton.setMaximumSize(new Dimension(350, 40));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setBackground(new Color(50, 50, 50));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        Color borderColor = new Color(0, 150, 255);
        Border defaultBorder = new LineBorder(Color.DARK_GRAY, 1);
        Border hoverBorder = new LineBorder(borderColor, 2);

        boton.setBorder(defaultBorder);

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBorder(hoverBorder);
                boton.setBackground(new Color(70, 70, 70));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBorder(defaultBorder);
                boton.setBackground(new Color(50, 50, 50));
            }
        });
        boton.addActionListener(e -> accion.run());

        panelContenedorBotones.add(boton);
        panelContenedorBotones.add(Box.createVerticalStrut(10));
    }
}