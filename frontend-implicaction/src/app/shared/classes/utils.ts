export class Utils {

  private static readonly NEW_LINE_PATTERN = /\r\n|\r|\n/g;
  private static readonly BR_MARKUP = '<br>';

  public static isEqual = (o1: any, o2: any): boolean => JSON.stringify(o1) === JSON.stringify(o2);

  public static replaceNewLineByBrMarkup = (s: string): string => s ? s.replace(Utils.NEW_LINE_PATTERN, Utils.BR_MARKUP) : '';
}
