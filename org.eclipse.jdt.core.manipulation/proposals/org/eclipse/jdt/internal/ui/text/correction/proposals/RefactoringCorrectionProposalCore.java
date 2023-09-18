package org.eclipse.jdt.internal.ui.text.correction.proposals;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import org.eclipse.core.resources.IFile;

import org.eclipse.text.edits.InsertEdit;

import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextChange;
import org.eclipse.ltk.core.refactoring.TextFileChange;

import org.eclipse.jdt.core.ICompilationUnit;

public class RefactoringCorrectionProposalCore extends LinkedCorrectionProposalCore {

	private final Refactoring fRefactoring;
	private RefactoringStatus fRefactoringStatus;

	public RefactoringCorrectionProposalCore(String name, ICompilationUnit cu, Refactoring refactoring, int relevance) {
		super(name, cu, null, relevance);
		fRefactoring= refactoring;
	}

	/**
	 * Can be overridden by clients to perform expensive initializations of the refactoring
	 *
	 * @param refactoring the refactoring
	 * @throws CoreException if something goes wrong during init
	 */
	protected void init(Refactoring refactoring) throws CoreException {
		// empty default implementation
	}

	@Override
	public TextChange createTextChange() throws CoreException {
		init(fRefactoring);
		fRefactoringStatus= fRefactoring.checkFinalConditions(new NullProgressMonitor());
		if (fRefactoringStatus.hasFatalError()) {
			TextFileChange dummyChange= new TextFileChange("fatal error", (IFile) getCompilationUnit().getResource()); //$NON-NLS-1$
			dummyChange.setEdit(new InsertEdit(0, "")); //$NON-NLS-1$
			return dummyChange;
		}
		return (TextChange) fRefactoring.createChange(new NullProgressMonitor());
	}

	/*
	 * @see org.eclipse.jdt.internal.ui.text.correction.proposals.CUCorrectionProposal#getAdditionalProposalInfo(org.eclipse.core.runtime.IProgressMonitor)
	 * @since 3.6
	 */
	@Override
	public Object getAdditionalProposalInfo(IProgressMonitor monitor) {
		if (fRefactoringStatus != null && fRefactoringStatus.hasFatalError()) {
			return fRefactoringStatus.getEntryWithHighestSeverity().getMessage();
		}
		return super.getAdditionalProposalInfo(monitor);
	}
}