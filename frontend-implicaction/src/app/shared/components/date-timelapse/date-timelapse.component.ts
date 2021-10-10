import {Component, Input, OnInit} from '@angular/core';

interface Timelapse {
  singular?: string;
  plural?: string;
}

@Component({
  selector: 'app-date-timelapse',
  templateUrl: './date-timelapse.component.html',
  styleUrls: ['./date-timelapse.component.scss']
})
export class DateTimelapseComponent implements OnInit {

  /**
   * Passé cette valeur en mois on n'affiche plus le nombre de semaines mais "ce mois"
   */
  private static readonly DATE_WITH_MONTH_THRESHOLD_IN_WEEKS = 3;

  /**
   * Passé cette valeur en jours on n'affiche plus le nombre de jours mais "cette semaine"
   */
  private static readonly DATE_WITH_WEEK_THRESHOLD_IN_DAYS = 6;

  /**
   * Passé ce nombre de secondes, on n'affiche plus "à l'instant" mais "il y a x secondes"
   */
  private static readonly NOW_THRESHOLD_IN_SECONDS = 10;

  /**
   * Passé cette valeur en heures, on n'affiche plus "il y a x heures" mais "aujourd'hui"
   */
  private static readonly TODAY_AT_THRESHOLD_IN_HOURS = 12;

  private static readonly SECONDS_IN_YEAR = 31557600;
  private static readonly SECONDS_IN_MONTH = 2629800;
  private static readonly SECONDS_IN_WEEK = 604800;
  private static readonly SECONDS_IN_DAY = 86400;
  private static readonly SECONDS_IN_HOUR = 3600;
  private static readonly SECONDS_IN_MINUTE = 60;

  @Input()
  date: Date;
  ellapsedTimeString = '';
  void;
  private readonly timelapses: { [name: string]: Timelapse } = {
    years: {
      singular: `l'année dernière`,
      plural: 'il y a {count} ans',
    },
    months: {
      singular: 'le mois dernier',
      plural: 'il y a {count} mois',
    },
    thisMonth: {
      singular: `il y a moins d'un mois`
    },
    weeks: {
      singular: 'cette semaine',
      plural: 'il y a {count} semaines'
    },
    days: {
      singular: 'hier',
      plural: 'il y a {count} jours',
    },
    today: {
      singular: `aujourd'hui`
    },
    hours: {
      singular: 'il y a une heure',
      plural: 'il y a {count} heures',
    },
    minutes: {
      singular: 'il y a une minute',
      plural: 'il y a {count} minutes',
    },
    seconds: {
      singular: `à l'instant`,
      plural: 'il y a {count} secondes',
    }
  };

  ngOnInit(): void {
    const elapsedTimeInSecond = Math.round((Date.now() - new Date(this.date).getTime()) / 1000);
    this.ellapsedTimeString = this.buildTimelapseString(elapsedTimeInSecond);
  }

  private buildTimelapseString(elapsedTimeInSecond: number): string {
    // SECONDES
    if (elapsedTimeInSecond < DateTimelapseComponent.SECONDS_IN_MINUTE) {
      return this.buildTimelapseForSeconds(elapsedTimeInSecond);
    }
    // MINUTES
    if (elapsedTimeInSecond < DateTimelapseComponent.SECONDS_IN_HOUR) {
      const elapsedTimeInMinute = Math.round(elapsedTimeInSecond / DateTimelapseComponent.SECONDS_IN_MINUTE);
      return this.buildTimelapseForMinutes(elapsedTimeInMinute);
    }
    // HEURES
    if (elapsedTimeInSecond < DateTimelapseComponent.SECONDS_IN_DAY) {
      const elapsedTimeInHour = Math.round(elapsedTimeInSecond / DateTimelapseComponent.SECONDS_IN_HOUR);
      return this.buildTimelapseForHours(elapsedTimeInHour);
    }
    // JOURS
    if (elapsedTimeInSecond < DateTimelapseComponent.SECONDS_IN_WEEK) {
      const elapsedTimeInDay = Math.round(elapsedTimeInSecond / DateTimelapseComponent.SECONDS_IN_DAY);
      return this.buildTimelapseForDays(elapsedTimeInDay);
    }
    // SEMAINE
    if (elapsedTimeInSecond < DateTimelapseComponent.SECONDS_IN_MONTH) {
      const elapsedTimeInWeek = Math.round(elapsedTimeInSecond / DateTimelapseComponent.SECONDS_IN_WEEK);
      return this.buildTimelapseForWeeks(elapsedTimeInWeek);
    }
    // MOIS
    if (elapsedTimeInSecond < DateTimelapseComponent.SECONDS_IN_YEAR) {
      const elapsedTimeInMonth = Math.round(elapsedTimeInSecond / DateTimelapseComponent.SECONDS_IN_MONTH);
      return this.buildTimelapseForMonths(elapsedTimeInMonth);
    }
    // ANNEE
    const elaplsedTimeInYear = Math.round(elapsedTimeInSecond / DateTimelapseComponent.SECONDS_IN_YEAR);
    return this.buildTimelapseForYears(elaplsedTimeInYear);
  }

  private buildTimelapseForYears(elaplsedTimeInYear: number): string {
    if (elaplsedTimeInYear > 1) {
      return this.timelapses.years.plural.replace('{count}', String(elaplsedTimeInYear));
    }
    return this.timelapses.years.singular;
  }

  private buildTimelapseForMonths(elapsedTimeInMonth: number): string {
    if (elapsedTimeInMonth > 1) {
      return this.timelapses.months.plural.replace('{count}', String(elapsedTimeInMonth));
    }
    return this.timelapses.months.singular;
  }

  private buildTimelapseForWeeks(elapsedTimeInWeek: number): string {
    if (elapsedTimeInWeek > DateTimelapseComponent.DATE_WITH_MONTH_THRESHOLD_IN_WEEKS) {
      return this.timelapses.thisMonth.singular;
    }
    if (elapsedTimeInWeek > 1) {
      return this.timelapses.weeks.plural.replace('{count}', String(elapsedTimeInWeek));
    }
    return this.timelapses.weeks.singular;
  }

  private buildTimelapseForDays(elapsedTimeInDay: number): string {
    if (elapsedTimeInDay > DateTimelapseComponent.DATE_WITH_WEEK_THRESHOLD_IN_DAYS) {
      return this.timelapses.weeks.singular;
    }
    if (elapsedTimeInDay > 1) {
      return this.timelapses.days.plural.replace('{count}', String(elapsedTimeInDay));
    }
    return this.timelapses.days.singular;
  }

  private buildTimelapseForHours(elapsedTimeinHour: number): string {
    if (elapsedTimeinHour > DateTimelapseComponent.TODAY_AT_THRESHOLD_IN_HOURS) {
      return this.timelapses.today.singular;
    }
    if (elapsedTimeinHour > 1) {
      return this.timelapses.hours.plural.replace('{count}', String(elapsedTimeinHour));
    }
    return this.timelapses.hours.singular;
  }

  private buildTimelapseForMinutes(elapsedTimeInMinute: number): string {
    if (elapsedTimeInMinute > 1) {
      return this.timelapses.minutes.plural.replace('{count}', String(elapsedTimeInMinute));
    }
    return this.timelapses.minutes.singular;
  }

  private buildTimelapseForSeconds(elapsedTimeInSecond: number): string {
    if (elapsedTimeInSecond <= DateTimelapseComponent.NOW_THRESHOLD_IN_SECONDS) {
      return this.timelapses.seconds.singular;
    }
    return this.timelapses.seconds.plural.replace('{count}', String(elapsedTimeInSecond));
  }
}

