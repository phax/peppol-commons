package com.helger.peppol.identifier.factory;

import java.io.Serializable;

import javax.annotation.Nullable;

import com.helger.peppol.identifier.generic.doctype.IDocumentTypeIdentifier;
import com.helger.peppol.identifier.generic.participant.IParticipantIdentifier;
import com.helger.peppol.identifier.generic.process.IProcessIdentifier;

/**
 * A generic factory interface that allows to easily switch between default
 * identifiers (<code>Simple...Identifier</code>), Peppol identifiers (
 * <code>Peppol...Identifier</code>) and BDXR identifiers (
 * <code>BDXR...Identifier</code>).
 *
 * @author Philip Helger
 */
public interface IIdentifierFactory extends Serializable
{
  /**
   * Create a new document type identifier.
   *
   * @param sScheme
   *        The scheme to be used.
   * @param sValue
   *        The value to be used.
   * @return <code>null</code> if the provided scheme and/or value are/is
   *         invalid.
   */
  @Nullable
  IDocumentTypeIdentifier createDocumentTypeIdentifier (@Nullable String sScheme, @Nullable String sValue);

  /**
   * Parse the provided URI encoded identifier as a document type identifier.
   * This is the reverse operation of
   * {@link com.helger.peppol.identifier.IdentifierHelper#getIdentifierURIEncoded(com.helger.peppol.identifier.IIdentifier)}
   *
   * @param sURIEncodedIdentifier
   *        The URI encoded identifier in the format <code>scheme::value</code>.
   *        It must NOT be percent encoded!
   * @return The created identifier or <code>null</code> if the passed
   *         identifier is not a valid URI encoded identifier
   */
  @Nullable
  IDocumentTypeIdentifier parseDocumentTypeIdentifier (@Nullable String sURIEncodedIdentifier);

  /**
   * Create a new participant identifier.
   *
   * @param sScheme
   *        The scheme to be used.
   * @param sValue
   *        The value to be used.
   * @return <code>null</code> if the provided scheme and/or value are/is
   *         invalid.
   */
  @Nullable
  IParticipantIdentifier createParticipantIdentifier (@Nullable String sScheme, @Nullable String sValue);

  /**
   * Parse the provided URI encoded identifier as a participant identifier. This
   * is the reverse operation of
   * {@link com.helger.peppol.identifier.IdentifierHelper#getIdentifierURIEncoded(com.helger.peppol.identifier.IIdentifier)}
   *
   * @param sURIEncodedIdentifier
   *        The URI encoded identifier in the format <code>scheme::value</code>.
   *        It must NOT be percent encoded!
   * @return The created identifier or <code>null</code> if the passed
   *         identifier is not a valid URI encoded identifier
   */
  @Nullable
  IParticipantIdentifier parseParticipantIdentifier (@Nullable String sURIEncodedIdentifier);

  /**
   * Create a new process identifier.
   *
   * @param sScheme
   *        The scheme to be used.
   * @param sValue
   *        The value to be used.
   * @return <code>null</code> if the provided scheme and/or value are/is
   *         invalid.
   */
  @Nullable
  IProcessIdentifier createProcessIdentifier (@Nullable String sScheme, @Nullable String sValue);

  /**
   * Parse the provided URI encoded identifier as a process identifier. This is
   * the reverse operation of
   * {@link com.helger.peppol.identifier.IdentifierHelper#getIdentifierURIEncoded(com.helger.peppol.identifier.IIdentifier)}
   *
   * @param sURIEncodedIdentifier
   *        The URI encoded identifier in the format <code>scheme::value</code>.
   *        It must NOT be percent encoded!
   * @return The created identifier or <code>null</code> if the passed
   *         identifier is not a valid URI encoded identifier
   */
  @Nullable
  IProcessIdentifier parseProcessIdentifier (@Nullable String sURIEncodedIdentifier);
}
