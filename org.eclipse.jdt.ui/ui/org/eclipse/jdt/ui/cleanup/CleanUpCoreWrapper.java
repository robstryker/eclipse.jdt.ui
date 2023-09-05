package org.eclipse.jdt.ui.cleanup;

import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import org.eclipse.ltk.core.refactoring.RefactoringStatus;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.manipulation.CleanUpContextCore;
import org.eclipse.jdt.core.manipulation.CleanUpOptionsCore;
import org.eclipse.jdt.core.manipulation.CleanUpRequirementsCore;
import org.eclipse.jdt.core.manipulation.ICleanUpFixCore;
import org.eclipse.jdt.core.refactoring.CompilationUnitChange;

import org.eclipse.jdt.internal.corext.fix.ICleanUpCore;

public class CleanUpCoreWrapper {
	public static ICleanUpCore wrap(final ICleanUp var) {
		return new ICleanUpCore() {

			@Override
			public void setOptions(CleanUpOptionsCore options) {
				var.setOptions(wrapOptions(options));
			}

			@Override
			public String[] getStepDescriptions() {
				return var.getStepDescriptions();
			}

			@Override
			public CleanUpRequirementsCore getRequirementsCore() {
				CleanUpRequirements req = var.getRequirements();
				return req.toCore();
			}

			@Override
			public RefactoringStatus checkPreConditions(IJavaProject project, ICompilationUnit[] compilationUnits, IProgressMonitor monitor) throws CoreException {
				return var.checkPreConditions(project, compilationUnits, monitor);
			}

			@Override
			public ICleanUpFixCore createFixCore(CleanUpContextCore context) throws CoreException {
				return var.createFix(new CleanUpContext(context.getCompilationUnit(), context.getAST()));
			}

			@Override
			public RefactoringStatus checkPostConditions(IProgressMonitor monitor) throws CoreException {
				return var.checkPostConditions(monitor);
			}
		};
	}


	public static ICleanUp wrap(final ICleanUpCore var) {
		return new ICleanUp() {


			@Override
			public void setOptions(CleanUpOptions options) {
				var.setOptions(options);
			}

			@Override
			public CleanUpRequirements getRequirements() {
				CleanUpRequirementsCore rc = var.getRequirementsCore();
				return new CleanUpRequirements(rc);
			}

			@Override
			public ICleanUpFix createFix(CleanUpContext context) throws CoreException {
				final ICleanUpFixCore c = var.createFixCore(context);
				return new ICleanUpFix() {
					@Override
					public CompilationUnitChange createChange(IProgressMonitor progressMonitor) throws CoreException {
						return c.createChange(progressMonitor);
					}
				};
			}

			@Override
			public String[] getStepDescriptions() {
				return var.getStepDescriptions();
			}

			@Override
			public RefactoringStatus checkPreConditions(IJavaProject project, ICompilationUnit[] compilationUnits, IProgressMonitor monitor) throws CoreException {
				return var.checkPreConditions(project, compilationUnits, monitor);
			}

			@Override
			public RefactoringStatus checkPostConditions(IProgressMonitor monitor) throws CoreException {
				return var.checkPostConditions(monitor);
			}
		};
	}



	private static CleanUpOptions wrapOptions(final CleanUpOptionsCore options) {
		return new CleanUpOptions() {

			@Override
			public Set<String> getKeys() {
				return options.getKeys();
			}

			@Override
			public void setOption(String key, String value) {
				options.setOption(key, value);
			}

			@Override
			public String getValue(String key) {
				return options.getValue(key);
			}

			@Override
			public boolean isEnabled(String key) {
				return options.isEnabled(key);
			}
		};
	}

}
