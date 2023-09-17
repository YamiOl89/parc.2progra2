package edu.formularios;

import edu.pizza.base.Pizza;
import edu.pizza.base.Topping;
import edu.pizza.especialidades.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class frmPizza {
    private JPanel jPanelPrincipal;
    private JComboBox<Topping> comboBoxToppings;
    private JTextField txtPizza;
    private JButton btnAddIngrediente;
    private JLabel lblTotal;
    private JList<Topping> lista1;
    private JButton btbPrepararPizza;
    private JRadioButton radioPequeña;
    private JRadioButton radioMediana;
    private JRadioButton radioGrande;
    private JComboBox<String> TiposPizza;
    private JLabel TipoPizza;
    private List<Topping> ingredientes = new ArrayList<>();
    private double total = 0;
    private DefaultListModel<Topping> modeloLista = new DefaultListModel<>();

    public JPanel getjPanelPrincipal() {
        return jPanelPrincipal;
    }

    public frmPizza() {
        cargarToppings();
        crearBotonesEspecialidades();
        configurarComboBoxTiposPizza();
        configurarRadioButtonsTamaño();
        configurarBotonAgregarIngrediente();
        configurarBotonPrepararPizza();
    }

    // Crear botones para las especialidades de pizza
    private void crearBotonesEspecialidades() {
        JButton[] botonesEspecialidades = {
                new JButton("Pizza Hawaiana"),
                new JButton("Pizza Pepperoni"),
                new JButton("Pizza Vegetariana"),
                new JButton("Pizza Margarita")
        };

        for (JButton boton : botonesEspecialidades) {
            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Pizza pizza = null;
                    String nombrePizza = boton.getText();

                    switch (nombrePizza) {
                        case "Pizza Hawaiana":
                            pizza = new PizzaHawaiana();
                            break;
                        case "Pizza Pepperoni":
                            pizza = new PizzaPeperoni();
                            break;
                        case "Pizza Vegetariana":
                            pizza = new PizzaVegetariana();
                            break;
                        case "Pizza Margarita":
                            pizza = new PizzaMargarita();
                            break;
                    }

                    if (pizza != null) {
                        pizza.prepare();
                    }
                }
            });
        }
    }

    // Configurar JComboBox de tipos de pizza
    private void configurarComboBoxTiposPizza() {
        DefaultComboBoxModel<String> pizzaModel = new DefaultComboBoxModel<>();
        String[] tiposPizza = {"Pizza Hawaiana", "Pizza Pepperoni", "Pizza Vegetariana", "Pizza Margarita"};
        for (String tipo : tiposPizza) {
            pizzaModel.addElement(tipo);
        }
        TiposPizza.setModel(pizzaModel);

        TiposPizza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPizza = (String) TiposPizza.getSelectedItem();
                Pizza pizza = null;

                switch (selectedPizza) {
                    case "Pizza Hawaiana":
                        pizza = new PizzaHawaiana();
                        break;
                    case "Pizza Pepperoni":
                        pizza = new PizzaPeperoni();
                        break;
                    case "Pizza Vegetariana":
                        pizza = new PizzaVegetariana();
                        break;
                    case "Pizza Margarita":
                        pizza = new PizzaMargarita();
                        break;
                }

                if (pizza != null) {
                    pizza.prepare();
                }
            }
        });
    }

    // Configurar radio buttons para los tamaños
    private void configurarRadioButtonsTamaño() {
        ButtonGroup tamañoGroup = new ButtonGroup();

        String[] tamaños = {"Pequeña", "Mediana", "Grande"};

        for (String tamaño : tamaños) {
            JRadioButton radioButton = new JRadioButton(tamaño);
            tamañoGroup.add(radioButton);
            radioButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Tamaño seleccionado: " + tamaño);
                }
            });
        }
    }

    // Configurar botón para agregar ingrediente
    private void configurarBotonAgregarIngrediente() {
        btnAddIngrediente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Topping ingrediente = (Topping) comboBoxToppings.getSelectedItem();
                modeloLista.addElement(ingrediente);
                lista1.setModel(modeloLista);
                total += ingrediente.getPrecio();
                lblTotal.setText(String.valueOf(total));
            }
        });
    }

    // Configurar botón para preparar la pizza
    private void configurarBotonPrepararPizza() {
        btbPrepararPizza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lista1.getModel().getSize() == 0) {
                    System.out.println("Debes agregar ingredientes...");
                } else {
                    Pizza pizza = new Pizza(txtPizza.getText());
                    Topping topi;
                    for (int i = 0; i < lista1.getModel().getSize(); i++) {
                        topi = (Topping) lista1.getModel().getElementAt(i);
                        pizza.addTopping(topi);
                    }

                    String tamañoSeleccionado = "";
                    double precioTamaño = 0;

                    if (radioPequeña.isSelected()) {
                        tamañoSeleccionado = "Pequeña";
                        precioTamaño = 10.0;
                    } else if (radioMediana.isSelected()) {
                        tamañoSeleccionado = "Mediana";
                        precioTamaño = 15.0;
                    } else if (radioGrande.isSelected()) {
                        tamañoSeleccionado = "Grande";
                        precioTamaño = 20.0;
                    }

                    double precioTotal = total + precioTamaño;

                    System.out.println("Pizza: " + pizza.getName());
                    System.out.println("Tamaño: " + tamañoSeleccionado);
                    System.out.println("Ingredientes: " + pizza.getToppings());
                    System.out.println("Precio Total: $" + precioTotal);
                }
            }
        });
    }



    // Cargar toppings
    private void cargarToppings() {
        ingredientes.add(new Topping("Champiñones", 10));
        ingredientes.add(new Topping("Tomate", 11));
        ingredientes.add(new Topping("Cebolla", 6));
        ingredientes.add(new Topping("Tacuazin", 9));
        ingredientes.add(new Topping("Salchica", 15));
        ingredientes.add(new Topping("Anchoas", 18));

        DefaultComboBoxModel<Topping> model = new DefaultComboBoxModel<>(ingredientes.toArray(new Topping[0]));
        comboBoxToppings.setModel(model);

        // Agregar un MouseListener para eliminar ingredientes haciendo doble clic
        comboBoxToppings.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Verificar si fue un doble clic
                    Object selectedItem = comboBoxToppings.getSelectedItem();
                    if (selectedItem instanceof Topping) {
                        Topping selectedTopping = (Topping) selectedItem;
                        total -= selectedTopping.getPrecio(); // Restar el precio del ingrediente eliminado al total
                        modeloLista.removeElement(selectedTopping); // Eliminar el ingrediente de la lista del modelo
                        lblTotal.setText(String.valueOf(total)); // Actualizar el total en la etiqueta
                    }
                }
            }
        });
    }
}
