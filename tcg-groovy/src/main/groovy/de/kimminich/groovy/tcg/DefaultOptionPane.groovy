package de.kimminich.groovy.tcg

import javax.swing.*

class DefaultOptionPane implements OptionPane {

    @Override
    String showInputDialog(String message) {
        JOptionPane.showInputDialog(message)
    }

    @Override
    def showMessageDialog(String message) {
        JOptionPane.showMessageDialog(null, message)
    }

}
