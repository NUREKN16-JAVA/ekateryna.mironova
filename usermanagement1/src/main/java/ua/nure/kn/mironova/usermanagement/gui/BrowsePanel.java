package ua.nure.kn.mironova.usermanagement.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ua.nure.kn.mironova.usermanagement.User;
import ua.nure.kn.mironova.usermanagement.db.DatabaseException;
import ua.nure.kn.mironova.usermanagement.util.Messages;

public class BrowsePanel extends JPanel implements ActionListener {
	
	private MainFrame parent;
	private JScrollPane tablePanel;
	private JTable userTable;
	private JPanel buttonPanel;
	private JButton addButton;
	private JButton editButton;
	private JButton deleteButton;
	private JButton detailsButton;
	private final String DELETE_MESSAGE = Messages.getString("BrowsePanel.delete_message"); //$NON-NLS-1$

	public BrowsePanel(MainFrame mainFrame) {
		this.parent = mainFrame;
		initialize();
	}

	private void initialize() {
		this.setName("browsePanel"); //$NON-NLS-1$
		this.setLayout(new BorderLayout());
		this.add(getTablePanel(), BorderLayout.CENTER);
		this.add(getButtonPanel(), BorderLayout.SOUTH);	
	}

	private JPanel getButtonPanel() {
		if(buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.add(getAddButton());
			buttonPanel.add(getEditButton());
			buttonPanel.add(getDeleteButton());
			buttonPanel.add(getDetailsButton());
		}
		return buttonPanel;
	}
	

	private JButton getAddButton() {
		if(addButton == null) {
			addButton = new JButton();
			addButton.setText(Messages.getString("BrowsePanel.add")); //localize //$NON-NLS-1$
			addButton.setName("addButton"); //$NON-NLS-1$
			addButton.setActionCommand("add"); //$NON-NLS-1$
			addButton.addActionListener(this);
		}
		return addButton;
	}
	
	private JButton getEditButton() {
		if(editButton == null) {
			editButton = new JButton();
			editButton.setText(Messages.getString("BrowsePanel.edit")); //localize //$NON-NLS-1$
			editButton.setName("editButton"); //$NON-NLS-1$
			editButton.setActionCommand("edit"); //$NON-NLS-1$
			editButton.addActionListener(this);
		}
		return editButton;
	}
	
	private JButton getDeleteButton() {
		if(deleteButton == null) {
			deleteButton = new JButton();
			deleteButton.setText(Messages.getString("BrowsePanel.delete")); //localize //$NON-NLS-1$
			deleteButton.setName("deleteButton"); //$NON-NLS-1$
			deleteButton.setActionCommand("delete"); //$NON-NLS-1$
			deleteButton.addActionListener(this);
		}
		return deleteButton;
	}
	
	private JButton getDetailsButton() {
		if(detailsButton == null) {
			detailsButton = new JButton();
			detailsButton.setText(Messages.getString("BrowsePanel.details")); //localize //$NON-NLS-1$
			detailsButton.setName("detailsButton"); //$NON-NLS-1$
			detailsButton.setActionCommand("details"); //$NON-NLS-1$
			detailsButton.addActionListener(this);
		}
		return detailsButton;
	}

	private JScrollPane getTablePanel() {
		if(tablePanel == null) {
			tablePanel = new JScrollPane(getUserTable());
		}
		return tablePanel;
	}

	private JTable getUserTable() {
		if(userTable == null) {
			userTable = new JTable();
			userTable.setName("userTable"); //$NON-NLS-1$
		}
		return userTable;
	}

	public void initTable() {
		UserTableModel model;
		try {
			model = new UserTableModel(parent.getDao().findAll());
		} catch (DatabaseException e) {
			model = new UserTableModel(new ArrayList());
			JOptionPane.showMessageDialog(this, e.getMessage(),
			Messages.getString("BrowsePanel.error"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
		}
		getUserTable().setModel(model);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if("add".equalsIgnoreCase(actionCommand)) { //$NON-NLS-1$
			this.setVisible(false);
			parent.showAddPanel();
		}
	else if(Messages.getString("BrowsePanel.2").equalsIgnoreCase(actionCommand)) { //$NON-NLS-1$
		this.setVisible(false);
        parent.showEditPanel();
		}
	else if("delete".equalsIgnoreCase(actionCommand)) { //$NON-NLS-1$
        int result = JOptionPane.showConfirmDialog(parent, DELETE_MESSAGE, Messages.getString("BrowsePanel.3"), JOptionPane.YES_NO_OPTION, //$NON-NLS-1$
                JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            try {
                parent.getDao().delete(getSelectedUser());
                getUserTable().setModel(new UserTableModel(parent.getDao().findAll()));
            } catch (DatabaseException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage(), Messages.getString("BrowsePanel.error"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
            }
        }
    }
	else if(Messages.getString("BrowsePanel.5").equalsIgnoreCase(actionCommand)) { //$NON-NLS-1$
        this.setVisible(false);
        parent.showDetailsPanel();
    	}
	}


	public User getSelectedUser() {
		int selectedRow = getUserTable().getSelectedRow();
        if (selectedRow == -1)
            return null;
        try {
            User user = parent.getDao().find((Long) getUserTable().getValueAt(selectedRow, 0));
            return user;
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), Messages.getString("BrowsePanel.error"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
        }
        return null;
	}
}
