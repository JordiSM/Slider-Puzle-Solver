/**
 * Practica 6 Algoritmos Avanzados - Ing Informática UIB
 *
 * @date 11/06/2023
 * @author JordiSM, jfher
 */
package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

/**
 * Panel lateral izquierdo de la ventana principal.
 */
public class LeftLateralPanel extends JPanel {

    private final View vista;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private JTextField tamPuzzle;
    private final JButton aceptaTam;
    private final JButton cambiaImagen;

    /**
     * Panel Lateral izquierdo encargado de la configuración del algoritmo y los
     * datos de la aplicación
     *
     * @param v JFrame View
     */
    public LeftLateralPanel(View v) {
        this.vista = v;

        this.setLayout(null);
        this.x = 10;
        this.y = this.vista.MARGENVER;
        this.width = this.vista.MARGENLAT - 20;
        this.height = this.vista.getHeight() - this.vista.MARGENVER - 40;

        this.setBounds(x, y, width, height);
        this.setBackground(new Color(245, 245, 220));
        this.setBorder(new LineBorder(Color.BLACK, 2));

        Font font = new Font("Arial", Font.BOLD, 16);
        tamPuzzle = new JTextField();
        tamPuzzle.setHorizontalAlignment(SwingConstants.CENTER);
        tamPuzzle.setBounds(10, 50, width - 20, 50);
        tamPuzzle.setBackground(new Color(135, 116, 89));
        tamPuzzle.setBorder(new LineBorder(Color.BLACK, 2));
        tamPuzzle.setFont(font);
        tamPuzzle.setForeground(Color.WHITE);
        this.add(tamPuzzle);

        JLabel label = new JLabel("Tamaño del tablero");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(10, 0, width - 20, 80);
        label.setFont(font);
        label.setForeground(new Color(135, 116, 89));
        this.add(label);

        this.aceptaTam = new JButton("OK");
        this.aceptaTam.setBounds(width / 2 - 10, 104, width / 2, 50);
        this.aceptaTam.setBackground(new Color(212, 191, 142));
        this.aceptaTam.setBorder(new LineBorder(Color.BLACK, 2));
        this.aceptaTam.setFont(font);
        this.aceptaTam.setForeground(Color.WHITE);
        this.aceptaTam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tamPuzzle.getText().matches("[0-9]+")) {
                    vista.cambiarPuzzle(new Puzzle(vista, vista.getGraphWidth(),
                            vista.getGraphHeight(),
                            Integer.parseInt(tamPuzzle.getText()),
                            vista.getPuzzle().getImagen()));
                }else{
                    Notification notifica = new Notification("La entrada no es válida");
                }
            }
        });
        this.add(aceptaTam);

        JLabel label2 = new JLabel("Selección de imagen");
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setBounds(10, 240, width - 20, 80);
        label2.setFont(font);
        label2.setForeground(new Color(135, 116, 89));
        this.add(label2);
        this.cambiaImagen = new JButton();
        this.cambiaImagen.setBounds(10, 290, width - 20, 200);

        /* Hago esto por que me da conflictos al usar this dentro del
        actionListener*/
        LeftLateralPanel punteroThis = this;
        this.cambiaImagen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jf = new JFileChooser();
                jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jf.setCurrentDirectory(new File("src/img/"));
                /*Para que no nos permita seleccionar más de un archivo*/
                jf.setMultiSelectionEnabled(false);
                if (jf.showOpenDialog(punteroThis) == JFileChooser.APPROVE_OPTION) {
                    /* Cojo la imagen seleccionada */
                    File selectedFile = jf.getSelectedFile();
                    /* Cogemos el path de la imagen */
                    String filePath = selectedFile.getAbsolutePath();

                    vista.cambiarPuzzle(new Puzzle(vista, vista.getGraphWidth(),
                            vista.getGraphHeight(),
                            vista.getPuzzle().getTamPuzzle(),
                            filePath));
                }
            }
        });
        ImageIcon iconoImg = new ImageIcon("src/img/image-icon.png");
        this.cambiaImagen.setIcon(iconoImg);
        this.cambiaImagen.setBackground(null);
        this.cambiaImagen.setBorder(null);
        this.add(this.cambiaImagen);

        this.init();
    }

    /**
     * Método encargado de la inicialización del JPanel y todos los componentes
     * que lo componen (JLabels, JComboBoxs y otros JPanels)
     */
    private void init() {

    }

}
