import { ApiClientError, type ApiError } from '@/types/api';

const API_BASE_URL =
  import.meta.env.VITE_API_BASE_URL?.replace(/\/$/, '') ??
  'http://localhost:8080/api/v1';

function isApiError(value: unknown): value is ApiError {
  if (!value || typeof value !== 'object') {
    return false;
  }

  const candidate = value as Partial<ApiError>;
  return (
    typeof candidate.status === 'number' &&
    typeof candidate.code === 'string' &&
    typeof candidate.message === 'string' &&
    Array.isArray(candidate.fieldErrors)
  );
}

async function parseResponseBody(response: Response): Promise<unknown> {
  if (response.status === 204) {
    return undefined;
  }

  const contentType = response.headers.get('content-type') ?? '';
  if (!contentType.includes('application/json')) {
    return response.text();
  }

  return response.json();
}

export async function apiRequest<T>(
  path: string,
  options: RequestInit = {},
): Promise<T> {
  const response = await fetch(`${API_BASE_URL}${path}`, {
    ...options,
    headers: {
      Accept: 'application/json',
      ...(options.body ? { 'Content-Type': 'application/json' } : {}),
      ...options.headers,
    },
  });

  const body = await parseResponseBody(response);

  if (!response.ok) {
    if (isApiError(body)) {
      throw new ApiClientError(body);
    }

    throw new Error(`Request failed with HTTP ${response.status}`);
  }

  return body as T;
}
