package ua.nure.kn.mironova.usermanagement.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ua.nure.kn.mironova.usermanagement.User;
import ua.nure.kn.mironova.usermanagement.db.DAOFactory;
import ua.nure.kn.mironova.usermanagement.db.UserDAO;
import ua.nure.kn.mironova.usermanagement.util.Messages;

public class MainFrame extends JFrame {
	
	private static final int FRAME_HIGHT = 600;
	private static final int FRAME_WIDTH = 800;
	private UserDAO dao;
	private JPanel contentPanel;
	private BrowsePanel browsePanel;
	private AddPanel addPanel;
	private EditPanel editPanel;
	private DetailsPanel detailsPanel;

	public MainFrame() {
		super();
		dao = DAOFactory.getInstance().getUserDAO();
		initialize();
	}
	
	public UserDAO getDao() {
		return dao;
	}


	private void initialize() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(FRAME_WIDTH, FRAME_HIGHT);
		this.setTitle(Messages.getString("MainFrame.user_management")); //localize //$NON-NLS-1$
		this.setContentPane(getContentPanel());
	}
	

	private JPanel getContentPanel() {
		if (contentPanel == null) {
			contentPanel = new JPanel();
			contentPanel.setLayout(new BorderLayout());
			contentPanel.add(getBrowsePanel(),BorderLayout.CENTER);
		}
		return contentPanel;
	}

	private JPanel getBrowsePanel() {
		if (browsePanel == null) {
			browsePanel = new BrowsePanel(this);
		}
		((BrowsePanel)browsePanel).initTable();
		return browsePanel;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void showAddPanel() {
		showPanel(getAddPanel());
	}
	
	public void showBrowsePanel() {
        showPanel(getBrowsePanel());    
    }

	private void showPanel(JPanel panel) {
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setVisible(true);
		panel.repaint();
	}

	private AddPanel getAddPanel() {
		if(addPanel==null) {
			addPanel = new AddPanel(this);
		}
		return addPanel;
	}

	public User getSelectedUser() {
		return ((BrowsePanel) browsePanel).getSelectedUser();
	}
	
	private EditPanel getEditPanel() {
        if (editPanel == null) {
            editPanel = new EditPanel(this);
        }
        return editPanel;
    }
	
	public void showEditPanel() {
		showPanel(getEditPanel());
		
	}

	private DetailsPanel getDetailsPanel() {
		 if (detailsPanel == null) {
	            detailsPanel = new DetailsPanel(this);
	        }
	        return detailsPanel;
	}

	public void showDetailsPanel() {
		showPanel(getDetailsPanel());
		
	}
}
