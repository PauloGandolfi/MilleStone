interface ErrorAlertProps {
  title?: string;
  message: string;
}

export function ErrorAlert({ title = 'Something went wrong', message }: ErrorAlertProps) {
  return (
    <div className="error-alert" role="alert">
      <strong>{title}</strong>
      <span>{message}</span>
    </div>
  );
}
