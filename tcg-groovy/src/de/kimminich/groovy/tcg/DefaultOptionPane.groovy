package de.kimminich.groovy.tcg

import javax.swing.JOptionPane

class DefaultOptionPane implements OptionPane {

    @Override
    String showInputDialog(String message) {
        return JOptionPane.showInputDialog(message)
    }

    @Override
    def showMessageDialog(String message) {
        JOptionPane.showMessageDialog(null, message)
    }

}
