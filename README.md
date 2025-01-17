# solarpos-cli

![CI](https://github.com/KlausBrunner/solarpos-cli/workflows/CI/badge.svg)

A simple command-line application to calculate topocentric solar coordinates and sunrise/sunset times, based
on [solarpositioning](https://github.com/KlausBrunner/solarpositioning). It supports timeseries and various output 
formats for easy processing by other tools to create e.g. [sun-path diagrams](https://github.com/KlausBrunner/sunpath-r/blob/main/sunpath.md).

Status: **early stage**, "alpha" quality. Basic functionality works, but lacks real-world testing and polish.

## Requirements

Java 17 or newer. (Unsure how to get a recent Java version? Try [Adoptium](https://adoptium.net/).)

## Usage

Get executable JAR for [latest release](https://github.com/KlausBrunner/solarpos-cli/releases/latest) (or build it
yourself) and run as usual:

```
java -jar solarpos-cli.jar
```

On Linux, macOS and other POSIX-like systems, it should be enough to set the executable flag and run the JAR directly.

Then, see built-in help.

```
Usage: solarpos-cli [-hV] [--show-inputs] [--deltat[=<deltaT>]]
                    [--format=<format>] [--timezone=<timezone>] <latitude>
                    <longitude> <dateTime> [COMMAND]
Calculates topocentric solar coordinates or sunrise/sunset times.
      <latitude>            latitude in decimal degrees (positive North of
                              equator)
      <longitude>           longitude in decimal degrees (positive East of
                              Greenwich)
      <dateTime>            date/time in ISO format yyyy[-MM[-dd[['T'][ ]HH:mm:
                              ss[.SSS][XXX['['VV']']]]]]. use 'now' for current
                              time and date.
      --deltat[=<deltaT>]   delta T in seconds; an estimate is used if this
                              option is given without a value
      --format=<format>     output format, one of HUMAN, CSV, JSON
  -h, --help                Show this help message and exit.
      --show-inputs         show all inputs in output
      --timezone=<timezone> timezone as offset (e.g. +01:00) and/or zone id (e.
                              g. America/Los_Angeles). overrides any timezone
                              info found in date/time parameter.
  -V, --version             Print version information and exit.
Commands:
  position  calculates topocentric solar coordinates
  sunrise   calculates sunrise, transit, and sunset
```

### Time series

There is some basic built-in support for calculating time series.

* If you pass only a year (e.g. 2023) or a year-month (e.g. 2023-01) to the sunrise command, you will get results for
  each day of that year or month.
* Similarly, the position command will calculate a time series of sun positions for the given day, month or even year.
  The interval is determined by the step option (default: 1 hour).
  
### Date and Time Formats

Dates and times should be given in ISO 8601 format like 2011-12-03T10:15:30+01:00 or a subset thereof, such as 2011-12-03 for a date without a time and timezone offset.

### Usage examples

Get today's sunrise and sunset for Madrid, Spain, in UTC:

```
solarpos-cli.jar 40.4168 -3.7038 now --timezone UTC sunrise
```

Get the sun's position in Stockholm, Sweden, on 15 January 2023 at 12:30 Central European Time:

```
solarpos-cli.jar 59.334 18.063 2023-01-15T12:30:00+01:00 position 
```

Get a time series of sun positions for Berlin Alexanderplatz on 15 January 2023, one position every 10 minutes, with CSV
output, in local timezone and using a delta T estimate:

```
solarpos-cli.jar 52.5219 13.4132 2023-01-15 --timezone Europe/Berlin --deltat --format=csv position --step=600
```

