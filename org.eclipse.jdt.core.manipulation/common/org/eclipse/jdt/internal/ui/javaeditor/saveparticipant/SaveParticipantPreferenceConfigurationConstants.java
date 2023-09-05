package org.eclipse.jdt.internal.ui.javaeditor.saveparticipant;

public interface SaveParticipantPreferenceConfigurationConstants {

	/**
     * Preference prefix that is prepended to the id of {@link SaveParticipantDescriptor save participants}.
     *
     * <p>
     * Value is of type <code>Boolean</code>.
     * </p>
     *
     * @see SaveParticipantDescriptor
     * @since 3.3
     */
    public static final String EDITOR_SAVE_PARTICIPANT_PREFIX= "editor_save_participant_";  //$NON-NLS-1$

	public static final String POSTSAVELISTENER_ID= "org.eclipse.jdt.ui.postsavelistener.cleanup"; //$NON-NLS-1$

}
