export const FILTER_CONSTANTS = {
  ALL: 'ALL',
  OPEN: 'OPEN',
  IN_PROGRESS: 'IN_PROGRESS',
  CLOSED: 'CLOSED'
} as const;

export type FilterType = typeof FILTER_CONSTANTS[keyof typeof FILTER_CONSTANTS];
