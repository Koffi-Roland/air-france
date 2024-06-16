export class DateHelper {

  private static start = new Date();

    /**
   * Convert a backend string used for date in the `Date` format of javascript.
   * @param str
   */
    public static convertToDate(str: string): Date {
        const splittedString = str.split('/');
        const day = splittedString[0];
        const month = splittedString[1];
        const year = splittedString[2];
        return new Date(parseInt(year, 10), parseInt(month, 10) - 1, parseInt(day, 10));
    }

    /**
     * Convert a `Date` into a specific format for the backend `dd/MM/yyyy`.
     * @param date
     */
    public static convertToString(date: Date): string {
        const day = date.getDate();
        const month = date.getMonth() + 1;
        const year = date.getUTCFullYear();
        return `${(day < 10) ? '0' + day : day}/${(month < 10) ? '0' + month : month}/${year}`;
    }

    public static startTimer() {
      this.start = new Date();
    }

    public static endTimer(task: string) {
      const now = new Date();
      console.log( task + ':', (now.getTime() - this.start.getTime()) / 1000, ' seconds');
    }

}
