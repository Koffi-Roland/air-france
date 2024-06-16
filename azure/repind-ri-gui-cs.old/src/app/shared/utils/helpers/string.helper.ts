export class StringHelper {

  /**
   * REPIND-2465
   * Escape special characters 'quotes' in an email.
   * @param email
   */
  public static escapeQuotesForEmails(email: string): string {
    return email.includes('"') ? email.split('"').join('\\"') : email;
  }

}
