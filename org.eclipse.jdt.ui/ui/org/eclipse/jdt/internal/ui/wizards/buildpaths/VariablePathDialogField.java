package org.eclipse.jdt.internal.ui.wizards.buildpaths;import org.eclipse.swt.SWT;import org.eclipse.swt.events.SelectionEvent;import org.eclipse.swt.events.SelectionListener;import org.eclipse.swt.widgets.Button;import org.eclipse.swt.widgets.Composite;import org.eclipse.swt.widgets.Control;import org.eclipse.swt.widgets.Label;import org.eclipse.swt.widgets.Shell;import org.eclipse.swt.widgets.Text;import org.eclipse.core.runtime.IPath;import org.eclipse.core.runtime.Path;import org.eclipse.jdt.core.JavaCore;import org.eclipse.jdt.internal.ui.JavaPlugin;import org.eclipse.jdt.internal.ui.wizards.dialogfields.IStringButtonAdapter;import org.eclipse.jdt.internal.ui.wizards.dialogfields.StringButtonDialogField;
public class VariablePathDialogField extends StringButtonDialogField {		private Button fBrowseVariableButton;	private String fVariableButtonLabel;			public VariablePathDialogField(IStringButtonAdapter adapter) {		super(adapter);	}	
	public void setVariableButtonLabel(String label) {		fVariableButtonLabel= label;	}		// ------- layout helpers			public Control[] doFillIntoGrid(Composite parent, int nColumns) {		assertEnoughColumns(nColumns);				Label label= getLabelControl(parent);		label.setLayoutData(gridDataForLabel(1));		Text text= getTextControl(parent);		text.setLayoutData(gridDataForText(nColumns - 3));		Control variableButton= getBrowseVariableControl(parent);		variableButton.setLayoutData(gridDataForControl(1));				Control browseButton= getChangeControl(parent);		browseButton.setLayoutData(gridDataForControl(1));		return new Control[] { label, text, variableButton, browseButton };	}		public int getNumberOfControls() {		return 4;		}			public Control getBrowseVariableControl(Composite parent) {		if (fBrowseVariableButton == null) {			assertCompositeNotNull(parent);						fBrowseVariableButton= new Button(parent, SWT.PUSH);			fBrowseVariableButton.setText(fVariableButtonLabel);			fBrowseVariableButton.setEnabled(isEnabled());			fBrowseVariableButton.addSelectionListener(new SelectionListener() {				public void widgetDefaultSelected(SelectionEvent e) {					chooseVariablePressed();				}				public void widgetSelected(SelectionEvent e) {					chooseVariablePressed();				}			});						}		return fBrowseVariableButton;	}		public IPath getPath() {		return new Path(getText());	}		public String getVariable() {		IPath path= getPath();		if (!path.isEmpty()) {			return path.segment(0);		}				return null;	}		public IPath getPathExtension() {		return new Path(getText()).removeFirstSegments(1).setDevice(null);	}			public IPath getResolvedPath() {		String variable= getVariable();		if (variable != null) {			IPath path= JavaCore.getClasspathVariable(variable);			if (path != null) {				return path.append(getPathExtension());			}		}		return null;	}				private Shell getShell() {		return JavaPlugin.getActiveWorkbenchShell();	}		private void chooseVariablePressed() {		String variable= getVariable();		ChooseVariableDialog dialog= new ChooseVariableDialog(getShell(), variable);		if (dialog.open() == dialog.OK) {			IPath newPath= new Path(dialog.getSelectedVariable()).append(getPathExtension());			setText(newPath.toString());		}	}		protected void updateEnableState() {		super.updateEnableState();		if (isOkToUse(fBrowseVariableButton)) {			fBrowseVariableButton.setEnabled(isEnabled());		}	}	}
