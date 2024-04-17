// Functions that are used to query our express API for data

export const FetchCustomer = async ({ customerId }: { customerId: string }) => {
  try {
    const response = await fetch(`/customers/${customerId}`);
    return await response.json();
  } catch (error) {
    console.error("Error fetching customer data:", error);
  }
};

export const FetchSubscriptions = async ({
  customerId,
}: {
  customerId: string;
}) => {
  try {
    const response = await fetch("/subscriptions/search", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        customerId: customerId,
      }),
    });
    return await response.json();
  } catch (error) {
    console.error("Error fetching customer data:", error);
  }
};
